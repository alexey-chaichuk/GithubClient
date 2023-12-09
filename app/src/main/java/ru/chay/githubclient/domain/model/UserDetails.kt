package ru.chay.githubclient.domain.model

data class UserDetails(
    val login: String,
    val fullName: String,
    val repositories: List<String>
)