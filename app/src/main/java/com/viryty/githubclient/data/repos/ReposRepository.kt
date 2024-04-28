package com.viryty.githubclient.data.repos

import androidx.annotation.WorkerThread
import com.viryty.githubclient.data.repos.remote.Repo
import com.viryty.githubclient.data.repos.remote.ReposApiState
import com.viryty.githubclient.data.repos.room.Download
import com.viryty.githubclient.data.repos.room.DownloadDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ReposRepository @Inject constructor(
    private val remote: ReposRemoteDataSource,
    private val local: DownloadDao
) {
    val downloads: Flow<List<Download>> = local.getAll()

    @WorkerThread
    suspend fun insert(download: Download) {
        local.insert(download)
    }

    fun getRepos(user: String): Flow<ReposApiState<List<Repo>>> =
        flow {
            emit(ReposApiState.success(remote.getRepos(user)))
        }.flowOn(Dispatchers.IO)
}