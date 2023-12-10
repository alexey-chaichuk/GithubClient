package ru.chay.githubclient.domain.usecase

import ru.chay.githubclient.domain.model.UserDetails
import ru.chay.githubclient.domain.repository.GithubRepository

class UserDetailsByLoginUseCase(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(username: String) : UserDetails {
        return repository.getUserDetails(username)
    }
}