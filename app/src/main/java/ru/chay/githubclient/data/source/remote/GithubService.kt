package ru.chay.githubclient.data.source.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.chay.githubclient.data.source.remote.dto.RepositoryDto
import ru.chay.githubclient.data.source.remote.dto.SearchResponseDto
import ru.chay.githubclient.data.source.remote.dto.UserDetailsDto
import ru.chay.githubclient.data.source.remote.dto.UserDto

interface GithubService {

    @GET("users")
    suspend fun getUsers(): List<UserDto>

    @GET("/search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): SearchResponseDto

    @GET("/users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): UserDetailsDto

    @GET("/users/{username}/repos")
    suspend fun getReposForUser(
        @Path("username") username: String
    ): List<RepositoryDto>

    companion object {
        private const val baseUrl = "https://api.github.com"

        fun create(): GithubService {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        }
    }
}