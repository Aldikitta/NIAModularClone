package com.aldikitta.data.model

import com.aldikitta.database.model.AuthorEntity
import com.aldikitta.network.model.NetworkAuthor

fun NetworkAuthor.asEntity() = AuthorEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    twitter = twitter,
    mediumPage = mediumPage,
    bio = bio,
)