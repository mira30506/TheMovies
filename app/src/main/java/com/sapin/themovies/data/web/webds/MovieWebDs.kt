package com.sapin.themovies.data.web.webds

import androidx.lifecycle.Observer
import com.sapin.themovies.data.web.Response.ResponseMovies
import com.sapin.themovies.data.web.api.MovieApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieWebDs @Inject constructor(private val apiService: MovieApiService) {

    fun getMovies(observer: Observer<Any>){
        CoroutineScope(Dispatchers.IO).launch {
           var response= apiService.getMovies().execute()
            if (response.isSuccessful)
                observer.onChanged(response.body())
            else
                observer.onChanged(response.code())
        }
    }

}