package com.aldikitta.data.repository

import com.aldikitta.datastore.NiaPreferencesDataSource
import com.aldikitta.model.data.UserData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class OfflineFirstUserDataRepository @Inject constructor(
    private val niaPreferencesDataSource: NiaPreferencesDataSource
) : UserDataRepository {

    override val userDataStream: Flow<UserData> =
        niaPreferencesDataSource.userDataStream

    override suspend fun setFollowedTopicIds(followedTopicIds: Set<String>) =
        niaPreferencesDataSource.setFollowedTopicIds(followedTopicIds)

    override suspend fun toggleFollowedTopicId(followedTopicId: String, followed: Boolean) =
        niaPreferencesDataSource.toggleFollowedTopicId(followedTopicId, followed)

    override suspend fun setFollowedAuthorIds(followedAuthorIds: Set<String>) =
        niaPreferencesDataSource.setFollowedAuthorIds(followedAuthorIds)

    override suspend fun toggleFollowedAuthorId(followedAuthorId: String, followed: Boolean) =
        niaPreferencesDataSource.toggleFollowedAuthorId(followedAuthorId, followed)

    override suspend fun updateNewsResourceBookmark(newsResourceId: String, bookmarked: Boolean) =
        niaPreferencesDataSource.toggleNewsResourceBookmark(newsResourceId, bookmarked)
}
