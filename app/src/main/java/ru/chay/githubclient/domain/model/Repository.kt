package ru.chay.githubclient.domain.model

data class Repository(
    val name: String,
    val description: String,
    val lastCommitDate: String,
    val forksCount: String,//Int,
    val defaultBranch: String,
    val starsCount: String,//Int,
    val language: String
)
