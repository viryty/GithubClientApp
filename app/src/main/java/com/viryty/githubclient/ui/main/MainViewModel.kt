package com.viryty.githubclient.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viryty.githubclient.data.repos.ReposRepository
import com.viryty.githubclient.data.repos.remote.Repo
import com.viryty.githubclient.data.repos.remote.ReposApiState
import com.viryty.githubclient.data.repos.remote.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ReposRepository
): ViewModel() {

    val reposState = MutableStateFlow(
        ReposApiState(
            Status.LOADING,
            listOf<Repo>(),
            ""
        )
    )

    fun getRepos(user: String) {
        reposState.value = ReposApiState.loading()

        viewModelScope.launch(Dispatchers.IO) {
            repository.getRepos(user)
                .catch {
                    reposState.value = ReposApiState.error(it.message.toString())
                }
                .collect {
                    reposState.value = ReposApiState.success(it.data)
                }
        }
    }
}