package ru.chay.githubclient.domain.repository

import ru.chay.githubclient.domain.model.User

interface GithubRepository {
    suspend fun getUsersByName(name: String) : List<User>
}