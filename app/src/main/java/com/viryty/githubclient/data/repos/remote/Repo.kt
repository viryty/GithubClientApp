package com.viryty.githubclient.data.repos.remote

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("archive_url")
    val archiveUrl: String,
    @SerializedName("downloads_url")
    val downloadsUrl: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("size")
    val size: Int,
    @SerializedName("url")
    val url: String,
)

data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("login")
    val login: String,
)