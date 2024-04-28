package com.viryty.githubclient.data.repos

import com.viryty.githubclient.data.repos.remote.ReposApi
import com.viryty.githubclient.data.repos.retrofit.RetrofitReposDataSource
import com.viryty.githubclient.data.repos.room.DownloadDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ReposModule {
    @Provides
    @Singleton
    fun provideReposRemoteDataSource(reposApi: ReposApi): ReposRemoteDataSource =
        RetrofitReposDataSource(reposApi)

    @Provides
    @Singleton
    fun provideReposRepository(remote: ReposRemoteDataSource, local: DownloadDao): ReposRepository =
        ReposRepository(remote, local)
}