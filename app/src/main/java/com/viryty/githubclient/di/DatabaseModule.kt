package com.viryty.githubclient.di

import android.content.Context
import androidx.room.Room
import com.viryty.githubclient.data.repos.room.AppDatabase
import com.viryty.githubclient.data.repos.room.DownloadDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideDownloadDao(database: AppDatabase): DownloadDao =
        database.downloadDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "download.db"
        ).build()
}