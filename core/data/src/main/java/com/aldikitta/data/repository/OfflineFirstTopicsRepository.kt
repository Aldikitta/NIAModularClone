package com.aldikitta.data.repository

import com.aldikitta.data.Synchronizer
import com.aldikitta.data.changeListSync
import com.aldikitta.data.model.asEntity
import com.aldikitta.database.dao.TopicDao
import com.aldikitta.database.model.TopicEntity
import com.aldikitta.database.model.asExternalModel
import com.aldikitta.datastore.ChangeListVersions
import com.aldikitta.model.data.Topic
import com.aldikitta.network.NiaNetworkDataSource
import com.aldikitta.network.model.NetworkTopic
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Disk storage backed implementation of the [TopicsRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstTopicsRepository @Inject constructor(
    private val topicDao: TopicDao,
    private val network: NiaNetworkDataSource,
) : TopicsRepository {

    override fun getTopicsStream(): Flow<List<Topic>> =
        topicDao.getTopicEntitiesStream()
            .map { it.map(TopicEntity::asExternalModel) }

    override fun getTopic(id: String): Flow<Topic> =
        topicDao.getTopicEntity(id).map { it.asExternalModel() }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::topicVersion,
            changeListFetcher = { currentVersion ->
                network.getTopicChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(topicVersion = latestVersion)
            },
            modelDeleter = topicDao::deleteTopics,
            modelUpdater = { changedIds ->
                val networkTopics = network.getTopics(ids = changedIds)
                topicDao.upsertTopics(
                    entities = networkTopics.map(NetworkTopic::asEntity)
                )
            }
        )
}
