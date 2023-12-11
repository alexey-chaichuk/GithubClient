package ru.chay.githubclient.domain.usecase

import ru.chay.githubclient.domain.model.User
import ru.chay.githubclient.domain.repository.GithubRepository

class SearchUsersByNameUseCase(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(name: String): List<User> {
        return repository.getUsersByName(name)
    }
}