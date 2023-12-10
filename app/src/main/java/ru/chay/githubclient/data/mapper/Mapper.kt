package ru.chay.githubclient.data.mapper

import ru.chay.githubclient.data.source.remote.dto.UserDetailsDto
import ru.chay.githubclient.data.source.remote.dto.UserDto
import ru.chay.githubclient.domain.model.User
import ru.chay.githubclient.domain.model.UserDetails

class Mapper {

    fun userDtoToDomain(dto: UserDto) = User(
        name = dto.login,
        followersUrl = dto.followers_url
    )

    fun userDetailsDtoToDomain(dto: UserDetailsDto) = UserDetails(
        name = dto.login,
        fullName = dto.name,
        followers = dto.followers,
        repositoriesUrl = dto.repos_url
    )
}