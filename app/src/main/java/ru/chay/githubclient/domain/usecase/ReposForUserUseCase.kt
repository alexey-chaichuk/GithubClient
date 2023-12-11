package ru.chay.githubclient.domain.usecase

import ru.chay.githubclient.domain.model.Repository
import ru.chay.githubclient.domain.repository.GithubRepository

class ReposForUserUseCase(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(username: String): List<Repository> {
        return repository.getReposForUser(username)
    }
}