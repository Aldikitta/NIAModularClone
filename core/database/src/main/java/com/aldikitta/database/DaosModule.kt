package com.aldikitta.database

import com.aldikitta.database.dao.AuthorDao
import com.aldikitta.database.dao.NewsResourceDao
import com.aldikitta.database.dao.TopicDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesAuthorDao(
        database: NiaDatabase,
    ): AuthorDao = database.authorDao()

    @Provides
    fun providesTopicsDao(
        database: NiaDatabase,
    ): TopicDao = database.topicDao()

    @Provides
    fun providesNewsResourceDao(
        database: NiaDatabase,
    ): NewsResourceDao = database.newsResourceDao()
}