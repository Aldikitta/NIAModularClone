package com.aldikitta.data.repository

import com.aldikitta.model.data.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    /**
     * Stream of [UserData]
     */
    val userDataStream: Flow<UserData>

    /**
     * Sets the user's currently followed topics
     */
    suspend fun setFollowedTopicIds(followedTopicIds: Set<String>)

    /**
     * Toggles the user's newly followed/unfollowed topic
     */
    suspend fun toggleFollowedTopicId(followedTopicId: String, followed: Boolean)

    /**
     * Sets the user's currently followed authors
     */
    suspend fun setFollowedAuthorIds(followedAuthorIds: Set<String>)

    /**
     * Toggles the user's newly followed/unfollowed author
     */
    suspend fun toggleFollowedAuthorId(followedAuthorId: String, followed: Boolean)

    /**
     * Updates the bookmarked status for a news resource
     */
    suspend fun updateNewsResourceBookmark(newsResourceId: String, bookmarked: Boolean)
}
