package ru.chay.githubclient.data.mapper

import ru.chay.githubclient.data.source.remote.dto.RepositoryDto
import ru.chay.githubclient.data.source.remote.dto.UserDetailsDto
import ru.chay.githubclient.data.source.remote.dto.UserDto
import ru.chay.githubclient.domain.model.Repository
import ru.chay.githubclient.domain.model.User
import ru.chay.githubclient.domain.model.UserDetails

class Mapper {

    fun userDtoToDomain(dto: UserDto) = User(
        name = dto.login,
        avatarUrl = dto.avatar_url,
        followersUrl = dto.followers_url,
    )

    fun userDetailsDtoToDomain(dto: UserDetailsDto) = UserDetails(
        name = dto.login,
        fullName = dto.name,
        followers = dto.followers,
        followersText = "${dto.followers} followers",
        repositoriesUrl = dto.repos_url
    )

    fun repositoryDtoToDomain(dto: RepositoryDto) = Repository(
        name = dto.full_name,
        description = dto.description ?: "",
        lastCommitDate = "updated: ${dto.updated_at}",
        forksCount = "${dto.forks_count} forks",
        defaultBranch = "default branch: ${dto.default_branch}",
        starsCount = "${dto.stargazers_count} stars",
        language = "language: ${dto.language}"
    )
}