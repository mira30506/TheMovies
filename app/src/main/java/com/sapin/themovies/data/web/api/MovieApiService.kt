package com.sapin.themovies.data.web.api


import com.sapin.themovies.data.web.Response.ResponseMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
val token="d636429773c9c3e7a41dbcefe53182e2"
interface MovieApiService {
    @GET("/3/discover/movie")
    fun getMovies(
        @Query("api_key") key:String=token,
        @Query("certification_country") cer:String="US",
        @Query("certification.lte") lte:String="G",
        @Query("sort_by") sortby:String="popularity.desc"
        ): Call<ResponseMovies>
}