package com.sapin.themovies.domain

import android.content.Context
import android.net.NetworkInfo
import androidx.lifecycle.Observer
import com.sapin.themovies.data.db.memory.MovieMemoryDS
import com.sapin.themovies.data.web.Response.ResponseMovies
import com.sapin.themovies.data.web.api.MovieApiService
import com.sapin.themovies.data.web.webds.MovieWebDs
import com.sapin.themovies.sys.utils.NetworkUltils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val movieWebDs: MovieWebDs,private val context: Context,private val memoryDS: MovieMemoryDS) {
    fun getMovies(observer: Observer<Any>){
        if(NetworkUltils.hasConnection(context))
           movieWebDs.getMovies(insertMovies(observer))
        else
            memoryDS.getMovies(observer)
    }
    private fun insertMovies(observer: Observer<Any>):Observer<Any>{
        return Observer {
            if(it is ResponseMovies)
                memoryDS.insertMovies(it.results)
            observer.onChanged(it)
        }
    }

}