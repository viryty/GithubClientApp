package com.viryty.githubclient.data.repos.remote

import com.viryty.githubclient.data.repos.remote.Status.*

data class ReposApiState<out T> (
    val status: Status,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T?): ReposApiState<T> =
            ReposApiState(SUCCESS, data, null)

        fun <T> error(msg: String): ReposApiState<T> =
            ReposApiState(ERROR, null, msg)

        fun <T> loading(): ReposApiState<T> =
            ReposApiState(LOADING, null, null)
    }
}