package com.viryty.githubclient.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.viryty.githubclient.data.repos.ReposRepository
import com.viryty.githubclient.data.repos.remote.Repo
import com.viryty.githubclient.data.repos.remote.ReposApiState
import com.viryty.githubclient.data.repos.remote.Status
import com.viryty.githubclient.data.repos.room.Download
import com.viryty.githubclient.ui.CurrentFragment
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

    private var currentFragment: CurrentFragment = CurrentFragment.SEARCH
    val downloads: LiveData<List<Download>> = repository.downloads.asLiveData()
    val reposState = MutableStateFlow(
        ReposApiState(
            Status.LOADING,
            listOf<Repo>(),
            ""
        )
    )

    fun insert(download: Download) = viewModelScope.launch {
        repository.insert(download)
    }

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

    fun setCurrentFragment(fragment: CurrentFragment) { currentFragment = fragment }
    fun getCurrentFragment(): CurrentFragment = currentFragment
}