package ru.chay.githubclient.ui.userslist

import ru.chay.githubclient.domain.model.User

sealed class UsersListUiEvent {
    data class updateUser(val user: User): UsersListUiEvent()
}
