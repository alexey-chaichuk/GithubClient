package ru.chay.githubclient.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val avatarUrl : String,
    val followersUrl: String,
    var fullName: String? = null,
    var followersText: String? = null
): Parcelable
