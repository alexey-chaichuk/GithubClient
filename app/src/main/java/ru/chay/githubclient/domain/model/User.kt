package ru.chay.githubclient.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val avatarUrl : String,
    val followersUrl: String,
    val fullName: String? = null,
    val followersText: String? = null
): Parcelable
