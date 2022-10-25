package com.aldikitta.data.repository

import com.aldikitta.data.Synchronizer
import com.aldikitta.data.changeListSync
import com.aldikitta.data.model.asEntity
import com.aldikitta.database.dao.AuthorDao
import com.aldikitta.database.model.AuthorEntity
import com.aldikitta.database.model.asExternalModel
import com.aldikitta.datastore.ChangeListVersions
import com.aldikitta.model.data.Author
import com.aldikitta.network.NiaNetworkDataSource
import com.aldikitta.network.model.NetworkAuthor
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Disk storage backed implementation of the [AuthorsRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstAuthorsRepository @Inject constructor(
    private val authorDao: AuthorDao,
    private val network: NiaNetworkDataSource,
) : AuthorsRepository {

    override fun getAuthorStream(id: String): Flow<Author> =
        authorDao.getAuthorEntityStream(id).map {
            it.asExternalModel()
        }

    override fun getAuthorsStream(): Flow<List<Author>> =
        authorDao.getAuthorEntitiesStream()
            .map { it.map(AuthorEntity::asExternalModel) }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::authorVersion,
            changeListFetcher = { currentVersion ->
                network.getAuthorChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(authorVersion = latestVersion)
            },
            modelDeleter = authorDao::deleteAuthors,
            modelUpdater = { changedIds ->
                val networkAuthors = network.getAuthors(ids = changedIds)
                authorDao.upsertAuthors(
                    entities = networkAuthors.map(NetworkAuthor::asEntity)
                )
            }
        )
}
