package com.aldikitta.data.repository

import com.aldikitta.data.Syncable
import com.aldikitta.model.data.Author
import kotlinx.coroutines.flow.Flow

interface AuthorsRepository : Syncable {
    /**
     * Gets the available Authors as a stream
     */
    fun getAuthorsStream(): Flow<List<Author>>

    /**
     * Gets data for a specific author
     */
    fun getAuthorStream(id: String): Flow<Author>
}
