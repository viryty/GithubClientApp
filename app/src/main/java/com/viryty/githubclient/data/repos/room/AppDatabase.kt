package com.viryty.githubclient.data.repos.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Download::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() { abstract fun downloadDao(): DownloadDao }