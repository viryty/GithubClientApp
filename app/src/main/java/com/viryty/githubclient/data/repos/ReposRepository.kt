package com.viryty.githubclient.data.repos

import com.viryty.githubclient.data.repos.remote.Repo
import com.viryty.githubclient.data.repos.remote.ReposApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ReposRepository @Inject constructor(
    private val remote: ReposRemoteDataSource
) {
    fun getRepos(user: String): Flow<ReposApiState<List<Repo>>> =
        flow {
            emit(ReposApiState.success(remote.getRepos(user)))
        }.flowOn(Dispatchers.IO)
}