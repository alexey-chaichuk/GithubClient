package ru.chay.githubclient.domain.usecase

import ru.chay.githubclient.domain.model.User
import ru.chay.githubclient.domain.repository.GithubRepository

class UpdateUserDetailsUseCase(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(user: User): User {
        val userDetails = repository.getUserDetails(user.name)
        with(user) {
            fullName = userDetails.fullName
            followersText = "${userDetails.followers} followers"
        }
        return user
    }
}