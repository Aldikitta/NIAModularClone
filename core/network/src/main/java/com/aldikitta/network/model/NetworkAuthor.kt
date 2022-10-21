package com.aldikitta.network.model

/**
 * Network representation of [Author]
 */
@kotlinx.serialization.Serializable
data class NetworkAuthor(
    val id: String,
    val name: String,
    val imageUrl: String,
    val twitter: String,
    val mediumPage: String,
    val bio: String,
)