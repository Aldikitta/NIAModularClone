package com.aldikitta.data.repository

import com.aldikitta.data.Synchronizer
import com.aldikitta.data.changeListSync
import com.aldikitta.data.model.*
import com.aldikitta.data.repository.NewsRepository
import com.aldikitta.database.dao.AuthorDao
import com.aldikitta.database.dao.NewsResourceDao
import com.aldikitta.database.dao.TopicDao
import com.aldikitta.database.model.AuthorEntity
import com.aldikitta.database.model.PopulatedNewsResource
import com.aldikitta.database.model.TopicEntity
import com.aldikitta.database.model.asExternalModel
import com.aldikitta.datastore.ChangeListVersions
import com.aldikitta.model.data.NewsResource
import com.aldikitta.network.NiaNetworkDataSource
import com.aldikitta.network.model.NetworkNewsResource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Disk storage backed implementation of the [NewsRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstNewsRepository @Inject constructor(
    private val newsResourceDao: NewsResourceDao,
    private val authorDao: AuthorDao,
    private val topicDao: TopicDao,
    private val network: NiaNetworkDataSource,
) : NewsRepository {

    override fun getNewsResourcesStream(): Flow<List<NewsResource>> =
        newsResourceDao.getNewsResourcesStream()
            .map { it.map(PopulatedNewsResource::asExternalModel) }

    override fun getNewsResourcesStream(
        filterAuthorIds: Set<String>,
        filterTopicIds: Set<String>
    ): Flow<List<NewsResource>> = newsResourceDao.getNewsResourcesStream(
        filterAuthorIds = filterAuthorIds,
        filterTopicIds = filterTopicIds
    )
        .map { it.map(PopulatedNewsResource::asExternalModel) }

    override suspend fun syncWith(synchronizer: Synchronizer) =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::newsResourceVersion,
            changeListFetcher = { currentVersion ->
                network.getNewsResourceChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(newsResourceVersion = latestVersion)
            },
            modelDeleter = newsResourceDao::deleteNewsResources,
            modelUpdater = { changedIds ->
                val networkNewsResources = network.getNewsResources(ids = changedIds)

                // Order of invocation matters to satisfy id and foreign key constraints!

                topicDao.insertOrIgnoreTopics(
                    topicEntities = networkNewsResources
                        .map(NetworkNewsResource::topicEntityShells)
                        .flatten()
                        .distinctBy(TopicEntity::id)
                )
                authorDao.insertOrIgnoreAuthors(
                    authorEntities = networkNewsResources
                        .map(NetworkNewsResource::authorEntityShells)
                        .flatten()
                        .distinctBy(AuthorEntity::id)
                )
                newsResourceDao.upsertNewsResources(
                    newsResourceEntities = networkNewsResources
                        .map(NetworkNewsResource::asEntity)
                )
                newsResourceDao.insertOrIgnoreTopicCrossRefEntities(
                    newsResourceTopicCrossReferences = networkNewsResources
                        .map(NetworkNewsResource::topicCrossReferences)
                        .distinct()
                        .flatten()
                )
                newsResourceDao.insertOrIgnoreAuthorCrossRefEntities(
                    newsResourceAuthorCrossReferences = networkNewsResources
                        .map(NetworkNewsResource::authorCrossReferences)
                        .distinct()
                        .flatten()
                )
            }
        )
}
