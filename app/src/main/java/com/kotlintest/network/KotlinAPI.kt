package com.kotlintest.network

import com.kotlintest.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface KotlinAPI {
    @GET("/3/discover/movie")
    fun getMovies(@Query("sort_by") sortBy: String,
                  @Query("api_key") apiKey: String): Observable<MoviesResponse>

    @GET("/3/discover/movie")
    fun getMovies(@Path("sort_by") sortBy: String,
                  @Path("api_key") apiKey: String,
                  @Path("page") page: Int): Observable<MoviesResponse>
}
