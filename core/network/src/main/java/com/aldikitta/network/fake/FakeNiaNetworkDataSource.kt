package com.aldikitta.network.fake

import com.aldikitta.common.core.network.Dispatcher
import com.aldikitta.common.core.network.NiaDispatchers
import com.aldikitta.network.NiaNetworkDataSource
import com.aldikitta.network.model.NetworkAuthor
import com.aldikitta.network.model.NetworkChangeList
import com.aldikitta.network.model.NetworkNewsResource
import com.aldikitta.network.model.NetworkTopic
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * [NiaNetworkDataSource] implementation that provides static news resources to aid development
 */
class FakeNiaNetworkDataSource @Inject constructor(
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json
) : NiaNetworkDataSource {
    override suspend fun getTopics(ids: List<String>?): List<NetworkTopic> =
        withContext(ioDispatcher) {
            networkJson.decodeFromString(FakeDataSource.topicsData)
        }

    override suspend fun getNewsResources(ids: List<String>?): List<NetworkNewsResource> =
        withContext(ioDispatcher) {
            networkJson.decodeFromString(FakeDataSource.data)
        }

    override suspend fun getAuthors(ids: List<String>?): List<NetworkAuthor> =
        withContext(ioDispatcher) {
            networkJson.decodeFromString(FakeDataSource.authors)
        }

    override suspend fun getTopicChangeList(after: Int?): List<NetworkChangeList> =
        getTopics().mapToChangeList(NetworkTopic::id)

    override suspend fun getAuthorChangeList(after: Int?): List<NetworkChangeList> =
        getAuthors().mapToChangeList(NetworkAuthor::id)

    override suspend fun getNewsResourceChangeList(after: Int?): List<NetworkChangeList> =
        getNewsResources().mapToChangeList(NetworkNewsResource::id)
}

/**
 * Converts a list of [T] to change list of all the items in it where [idGetter] defines the
 * [NetworkChangeList.id]
 */
private fun <T> List<T>.mapToChangeList(
    idGetter: (T) -> String
) = mapIndexed { index, item ->
    NetworkChangeList(
        id = idGetter(item),
        changeListVersion = index,
        isDelete = false,
    )
}
