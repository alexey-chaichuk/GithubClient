package ru.chay.githubclient.ui.userdetails

import ru.chay.githubclient.domain.model.UserDetails

sealed class UserDetailsUiState {
    data object Loading: UserDetailsUiState()
    data class Success(val user: UserDetails): UserDetailsUiState()
    data class Error(val exception: Throwable): UserDetailsUiState()
}