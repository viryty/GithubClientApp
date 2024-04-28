package com.viryty.githubclient.data.repos.remote

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ReposApi {
    @Headers("X-GitHub-Api-Version: 2022-11-28")
    @GET("users/{user}/repos")
    suspend fun getRepos(@Path("user") user: String): List<Repo>
}
