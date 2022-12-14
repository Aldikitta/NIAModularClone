package com.aldikitta.data.repository

import com.aldikitta.data.Syncable
import com.aldikitta.model.data.NewsResource
import kotlinx.coroutines.flow.Flow

/**
 * Data layer implementation for [NewsResource]
 */
interface NewsRepository : Syncable {
    /**
     * Returns available news resources as a stream.
     */
    fun getNewsResourcesStream(): Flow<List<NewsResource>>

    /**
     * Returns available news resources as a stream filtered by authors or topics.
     */
    fun getNewsResourcesStream(
        filterAuthorIds: Set<String> = emptySet(),
        filterTopicIds: Set<String> = emptySet(),
    ): Flow<List<NewsResource>>
}
