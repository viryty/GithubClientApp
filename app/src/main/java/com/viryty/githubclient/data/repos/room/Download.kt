package com.viryty.githubclient.data.repos.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Download(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "owner")
    val owner: String,
    @ColumnInfo(name = "name_repo")
    val nameRepo: String,
)
