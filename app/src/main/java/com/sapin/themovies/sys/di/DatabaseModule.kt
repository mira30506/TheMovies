package com.sapin.themovies.sys.di

import android.content.Context
import androidx.room.Room
import com.sapin.themovies.data.db.AppDatabase
import com.sapin.themovies.data.db.daos.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context):AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-name"
        ).build()
    }


    @Singleton
    @Provides
    fun providesMovieDao(appDatabase: AppDatabase):MovieDao{
        return appDatabase.movieDao()
    }

}