package com.sapin.themovies.data.db.memory

import androidx.lifecycle.Observer
import com.sapin.themovies.data.db.daos.MovieDao
import com.sapin.themovies.data.db.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieMemoryDS @Inject constructor(private val movieDao: MovieDao) {

   suspend fun getMovies():List<Movie>{
       return withContext(Dispatchers.IO) {
           movieDao.getMovies()
       }
   }

   suspend fun insertMovies(movies: List<Movie>){
          withContext(Dispatchers.IO) {
              movieDao.allData()
              movieDao.insert(movies)
          }

    }
}