package com.sapin.themovies.data.web.webds


import com.sapin.themovies.data.web.Response.ResponseMovies
import com.sapin.themovies.data.web.api.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieWebDs @Inject constructor(private val apiService: MovieApiService) {
   suspend fun getMovies():ResponseMovies?{
       return withContext(Dispatchers.IO){
           var response= apiService.getMovies()
           if (response.isSuccessful && response.code()==200)     response.body()
           else null
        }
    }
}