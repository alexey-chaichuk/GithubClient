package ru.chay.githubclient.domain.model

data class UserDetails(
    val name: String,
    val fullName: String?,
    val followers: Int,
    val followersText: String,
    val repositoriesUrl: String
)