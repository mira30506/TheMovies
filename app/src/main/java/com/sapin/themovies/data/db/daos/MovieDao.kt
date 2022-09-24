package com.sapin.themovies.data.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sapin.themovies.data.db.model.Movie
import org.intellij.lang.annotations.JdkConstants



@Dao
interface MovieDao {

    @Query("Select * from Movie")
    fun getMovies():List<Movie>

    @Insert
    fun insert(lista:List<Movie>)
}