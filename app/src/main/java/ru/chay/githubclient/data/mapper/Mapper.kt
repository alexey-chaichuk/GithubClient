package ru.chay.githubclient.data.mapper

import ru.chay.githubclient.data.source.remote.dto.UserDto
import ru.chay.githubclient.domain.model.User

class Mapper {

    fun userDtoToDomain(dto: UserDto) = User(
        name = dto.login,
        followersUrl = dto.followers_url
    )
}