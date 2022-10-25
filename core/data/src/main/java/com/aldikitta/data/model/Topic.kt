package com.aldikitta.data.model

import com.aldikitta.database.model.TopicEntity
import com.aldikitta.network.model.NetworkTopic

fun NetworkTopic.asEntity() = TopicEntity(
    id = id,
    name = name,
    shortDescription = shortDescription,
    longDescription = longDescription,
    url = url,
    imageUrl = imageUrl
)