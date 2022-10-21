package com.aldikitta.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aldikitta.database.dao.AuthorDao
import com.aldikitta.database.dao.NewsResourceDao
import com.aldikitta.database.dao.TopicDao
import com.aldikitta.database.model.*
import com.aldikitta.database.util.InstantConverter
import com.aldikitta.database.util.NewsResourceTypeConverter

@Database(
    entities = [
        AuthorEntity::class,
        NewsResourceAuthorCrossRef::class,
        NewsResourceEntity::class,
        NewsResourceTopicCrossRef::class,
        TopicEntity::class,
    ],
    version = 1,
    autoMigrations = [
//        AutoMigration(from = 1, to = 2),
    ],
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
    NewsResourceTypeConverter::class,
)
abstract class NiaDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
    abstract fun authorDao(): AuthorDao
    abstract fun newsResourceDao(): NewsResourceDao
}
