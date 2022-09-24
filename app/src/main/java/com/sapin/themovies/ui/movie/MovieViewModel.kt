package com.sapin.themovies.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.sapin.themovies.data.db.model.Movie
import com.sapin.themovies.data.web.Response.ResponseMovies
import com.sapin.themovies.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :ViewModel(){
    val responseMovies :MutableLiveData<List<Movie>>
    var error:MutableLiveData<String>
    init {
        responseMovies= MutableLiveData()
        error= MutableLiveData()
    }
    fun getMovies(){
        movieRepository.getMovies(setMovies())
    }
    fun setMovies():Observer<Any>{
        return Observer{
            if(it is ResponseMovies)
                responseMovies.postValue(it.results)
            else if (it is String)
                error.postValue(it)
            else
                responseMovies.postValue(it as List<Movie>)

        }
    }
}