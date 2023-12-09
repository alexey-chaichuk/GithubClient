package ru.chay.githubclient.ui.userslist

import ru.chay.githubclient.domain.model.User

sealed class UsersListUiState {
    data class Success(val users: List<User>): UsersListUiState()
    data class Error(val exception: Throwable): UsersListUiState()
}