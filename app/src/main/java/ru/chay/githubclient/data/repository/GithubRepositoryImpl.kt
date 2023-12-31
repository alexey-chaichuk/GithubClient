package ru.chay.githubclient.data.repository

import ru.chay.githubclient.data.mapper.Mapper
import ru.chay.githubclient.data.source.remote.GithubService
import ru.chay.githubclient.domain.model.Repository
import ru.chay.githubclient.domain.model.User
import ru.chay.githubclient.domain.model.UserDetails
import ru.chay.githubclient.domain.repository.GithubRepository

class GithubRepositoryImpl(
    private val service: GithubService,
    private val mapper: Mapper = Mapper()
) : GithubRepository {

    override suspend fun getUsersByName(query: String): List<User> {
        return service.searchUsers(query).items.map {
            mapper.userDtoToDomain(it)
        }
    }

    override suspend fun getUserDetails(username: String): UserDetails {
        return mapper.userDetailsDtoToDomain(
            service.getUser(username)
        )
    }

    override suspend fun getReposForUser(username: String): List<Repository> {
        return service.getReposForUser(username).map {
            mapper.repositoryDtoToDomain(it)
        }
    }
}