package com.kotlintest

import java.io.Serializable

/**
 * Created by alex on 6/26/17.
 */
data class Movie(
        var vote_count: String,
        var id: Int,
        var video: Boolean,
        var vote_average: Double,
        var title: String,
        var popularity: Double,
        var poster_path: String,
        var original_language: String,
        var original_title: String,
        var genre_ids: MutableList<Int>,
        var backdrop_path: String,
        var adult: Boolean,
        var overview: String,
        var release_date: String) : Serializable

data class MoviesResponse(
        val page: Int,
        val total_results: Int,
        val results: MutableList<Movie>) : Serializable
