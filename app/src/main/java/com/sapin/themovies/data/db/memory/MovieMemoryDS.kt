package com.sapin.themovies.data.db.memory

import androidx.lifecycle.Observer
import com.sapin.themovies.data.db.daos.MovieDao
import com.sapin.themovies.data.db.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieMemoryDS @Inject constructor(private val movieDao: MovieDao) {

    fun getMovies()= movieDao.getMovies()




    fun insertMovies(movies: List<Movie>){
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.allData()
            movieDao.insert(movies)
        }
    }
}