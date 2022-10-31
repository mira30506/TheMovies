package com.sapin.themovies.data.db.daos

import androidx.room.*
import com.sapin.themovies.data.db.model.Movie
import org.intellij.lang.annotations.JdkConstants



@Dao
interface MovieDao {

    @Query("Select * from Movie")
    suspend   fun getMovies():List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insert(lista:List<Movie>)


    @Query("delete from  Movie")
   suspend fun allData()
}