package com.aldikitta.data.repository

import com.aldikitta.data.Syncable
import com.aldikitta.model.data.Topic
import kotlinx.coroutines.flow.Flow

interface TopicsRepository : Syncable {
    /**
     * Gets the available topics as a stream
     */
    fun getTopicsStream(): Flow<List<Topic>>

    /**
     * Gets data for a specific topic
     */
    fun getTopic(id: String): Flow<Topic>
}
