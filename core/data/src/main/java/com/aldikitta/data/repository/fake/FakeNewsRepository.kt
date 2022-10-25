package com.aldikitta.data.repository.fake

import com.aldikitta.common.core.network.Dispatcher
import com.aldikitta.common.core.network.NiaDispatchers
import com.aldikitta.data.Synchronizer
import com.aldikitta.data.model.asEntity
import com.aldikitta.data.repository.NewsRepository
import com.aldikitta.database.model.NewsResourceEntity
import com.aldikitta.database.model.asExternalModel
import com.aldikitta.model.data.NewsResource
import com.aldikitta.network.fake.FakeDataSource
import com.aldikitta.network.model.NetworkNewsResource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Fake implementation of the [NewsRepository] that retrieves the news resources from a JSON String.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeNewsRepository @Inject constructor(
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json
) : NewsRepository {

    override fun getNewsResourcesStream(): Flow<List<NewsResource>> =
        flow {
            emit(
                networkJson.decodeFromString<List<NetworkNewsResource>>(FakeDataSource.data)
                    .map(NetworkNewsResource::asEntity)
                    .map(NewsResourceEntity::asExternalModel)
            )
        }
            .flowOn(ioDispatcher)

    override fun getNewsResourcesStream(
        filterAuthorIds: Set<String>,
        filterTopicIds: Set<String>,
    ): Flow<List<NewsResource>> =
        flow {
            emit(
                networkJson.decodeFromString<List<NetworkNewsResource>>(FakeDataSource.data)
                    .filter {
                        it.authors.intersect(filterAuthorIds).isNotEmpty() ||
                            it.topics.intersect(filterTopicIds).isNotEmpty()
                    }
                    .map(NetworkNewsResource::asEntity)
                    .map(NewsResourceEntity::asExternalModel)
            )
        }
            .flowOn(ioDispatcher)

    override suspend fun syncWith(synchronizer: Synchronizer) = true
}
