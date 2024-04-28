package com.viryty.githubclient.data.repos.retrofit

import com.viryty.githubclient.data.repos.ReposRemoteDataSource
import com.viryty.githubclient.data.repos.remote.Repo
import com.viryty.githubclient.data.repos.remote.ReposApi
import javax.inject.Inject

class RetrofitReposDataSource @Inject constructor(
    private val api: ReposApi
): ReposRemoteDataSource {
    override suspend fun getRepos(user: String): List<Repo> =
        api.getRepos(user)
}