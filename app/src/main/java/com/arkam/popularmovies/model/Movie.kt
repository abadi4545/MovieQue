package com.arkam.popularmovies.model

data class MovieResponse(
    val page: String,
    val total_pages: String,
    val results: ArrayList<Movie>
)

data class Movie(
    var id: String,
    var original_title: String,
    var poster_path: String,
    var backdrop_path: String
)

data class MovieSearch(
    val id: String,
    val genres: ArrayList<Genre>,
    val poster_path: String,
    val backdrop_path: String,
    val original_title: String,
    val overview: String,
    val release_date: String,
    val tagline: String,
    val vote_average: String
)

data class Genre(
    val name: String
)

data class MovieCastResponse(
    val id: Int = 0,
    val cast: ArrayList<MovieCast>

)

data class MovieCast(
    val cast_id: Int,
    val name: String,
    val profile_path: String,
    var id: String
)

data class SimilarResponse(
    val page: Int = 0,
    val results: ArrayList<Similar>

)

data class Similar(
    val cast_id: Int,
    val id: Int,
    val original_title: String,
    val poster_path: String
)

data class VideoResponse(
    val results: ArrayList<Video>
)

data class Video(
    val id: String,
    val key: String,
    val name: String
)