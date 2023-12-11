package ru.chay.githubclient.domain.repository

import ru.chay.githubclient.domain.model.Repository
import ru.chay.githubclient.domain.model.User
import ru.chay.githubclient.domain.model.UserDetails

interface GithubRepository {
    suspend fun getUsersByName(query: String): List<User>
    suspend fun getUserDetails(username: String): UserDetails
    suspend fun getReposForUser(username: String): List<Repository>
}