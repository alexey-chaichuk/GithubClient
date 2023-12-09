package ru.chay.githubclient.di

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.chay.githubclient.data.repository.GithubRepositoryImpl
import ru.chay.githubclient.data.source.remote.GithubService
import ru.chay.githubclient.domain.repository.GithubRepository
import ru.chay.githubclient.domain.usecase.SearchUsersByNameUseCase

interface AppModule {
    val githubService: GithubService
    val githubRepository: GithubRepository
    val searchUsersByNameUseCase: SearchUsersByNameUseCase

    class Base(
        private val appContext: Context
    ): AppModule {

        override val githubService: GithubService by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        }

        override val githubRepository: GithubRepository by lazy {
            GithubRepositoryImpl(githubService)
        }

        override val searchUsersByNameUseCase: SearchUsersByNameUseCase by lazy {
            SearchUsersByNameUseCase(githubRepository)
        }
    }
}