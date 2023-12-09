package ru.chay.githubclient.di

import android.content.Context
import ru.chay.githubclient.data.repository.GithubRepositoryImpl
import ru.chay.githubclient.data.source.remote.GithubService
import ru.chay.githubclient.domain.repository.GithubRepository
import ru.chay.githubclient.domain.usecase.SearchUsersByNameUseCase
import ru.chay.githubclient.domain.usecase.UserDetailsByLoginUseCase

interface AppModule {
    val githubService: GithubService
    val githubRepository: GithubRepository
    val searchUsersByNameUseCase: SearchUsersByNameUseCase
    val userDetailsByLoginUseCase: UserDetailsByLoginUseCase

    class Base(
        private val appContext: Context
    ): AppModule {

        override val githubService: GithubService by lazy {
            GithubService.create()
        }

        override val githubRepository: GithubRepository by lazy {
            GithubRepositoryImpl(githubService)
        }

        override val searchUsersByNameUseCase: SearchUsersByNameUseCase by lazy {
            SearchUsersByNameUseCase(githubRepository)
        }

        override val userDetailsByLoginUseCase: UserDetailsByLoginUseCase by lazy {
            UserDetailsByLoginUseCase(githubRepository)
        }
    }
}