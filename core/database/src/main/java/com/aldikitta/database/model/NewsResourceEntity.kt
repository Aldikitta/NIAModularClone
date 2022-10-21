package com.aldikitta.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aldikitta.model.data.NewsResource
import com.aldikitta.model.data.NewsResourceType
import kotlinx.datetime.Instant

/**
 * Defines an NiA news resource.
 */
@Entity(
    tableName = "news_resources"
)
data class NewsResourceEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String,
    val url: String,
    @ColumnInfo(name = "header_image_url")
    val headerImageUrl: String?,
    @ColumnInfo(name = "publish_date")
    val publishDate: Instant,
    val type: NewsResourceType,
)

fun NewsResourceEntity.asExternalModel() = NewsResource(
    id = id,
    title = title,
    content = content,
    url = url,
    headerImageUrl = headerImageUrl,
    publishDate = publishDate,
    type = type,
    authors = listOf(),
    topics = listOf()
)
