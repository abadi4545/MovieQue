package com.arkam.popularmovies.model

data class ImageResponse(
    val id: Int,
    val backdrops: List<ImageOne>,
    val posters: List<ImageSecond>
)

data class ImageOne(
    var height: Int,
    var width: Int
)

data class ImageSecond(
    var height: Int,
    var width: Int
)
