package com.sapin.themovies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sapin.themovies.data.db.daos.MovieDao
import com.sapin.themovies.data.db.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}