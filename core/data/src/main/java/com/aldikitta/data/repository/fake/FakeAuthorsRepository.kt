package com.aldikitta.data.repository.fake

import com.aldikitta.common.core.network.Dispatcher
import com.aldikitta.common.core.network.NiaDispatchers
import com.aldikitta.data.Synchronizer
import com.aldikitta.data.repository.AuthorsRepository
import com.aldikitta.model.data.Author
import com.aldikitta.network.fake.FakeDataSource
import com.aldikitta.network.model.NetworkAuthor
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Fake implementation of the [AuthorsRepository] that returns hardcoded authors.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeAuthorsRepository @Inject constructor(
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
) : AuthorsRepository {

    override fun getAuthorsStream(): Flow<List<Author>> = flow {
        emit(
            networkJson.decodeFromString<List<NetworkAuthor>>(FakeDataSource.authors).map {
                Author(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    twitter = it.twitter,
                    mediumPage = it.mediumPage,
                    bio = it.bio,
                )
            }
        )
    }
        .flowOn(ioDispatcher)

    override fun getAuthorStream(id: String): Flow<Author> {
        return getAuthorsStream().map { it.first { author -> author.id == id } }
    }

    override suspend fun syncWith(synchronizer: Synchronizer) = true
}
