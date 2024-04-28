package com.viryty.githubclient.data.repos

import com.viryty.githubclient.data.repos.remote.Repo

interface ReposRemoteDataSource {
    suspend fun getRepos(user: String): List<Repo>
}