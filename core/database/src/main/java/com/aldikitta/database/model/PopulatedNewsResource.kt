package com.aldikitta.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.aldikitta.model.data.NewsResource

/**
 * External data layer representation of a fully populated NiA news resource
 */
data class PopulatedNewsResource(
    @Embedded
    val entity: NewsResourceEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NewsResourceAuthorCrossRef::class,
            parentColumn = "news_resource_id",
            entityColumn = "author_id",
        )
    )
    val authors: List<AuthorEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NewsResourceTopicCrossRef::class,
            parentColumn = "news_resource_id",
            entityColumn = "topic_id",
        )
    )
    val topics: List<TopicEntity>
)

fun PopulatedNewsResource.asExternalModel() = NewsResource(
    id = entity.id,
    title = entity.title,
    content = entity.content,
    url = entity.url,
    headerImageUrl = entity.headerImageUrl,
    publishDate = entity.publishDate,
    type = entity.type,
    authors = authors.map(AuthorEntity::asExternalModel),
    topics = topics.map(TopicEntity::asExternalModel)
)
