package ru.chay.githubclient.data.source.remote

import retrofit2.http.GET
import ru.chay.githubclient.data.source.remote.dto.UserDto

interface GithubService {

    @GET("users")
    suspend fun getUsers(): List<UserDto>

}