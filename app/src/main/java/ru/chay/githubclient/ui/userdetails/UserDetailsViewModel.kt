package ru.chay.githubclient.ui.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.chay.githubclient.App
import ru.chay.githubclient.domain.usecase.UserDetailsByLoginUseCase
import ru.chay.githubclient.ui.userslist.UsersListUiState
import ru.chay.githubclient.ui.userslist.UsersListViewModel

class UserDetailsViewModel(
    private val userDetailsByLoginUseCase: UserDetailsByLoginUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UserDetailsUiState>(UserDetailsUiState.Loading)
    val uiState: StateFlow<UserDetailsUiState> = _uiState
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                UserDetailsViewModel(
                    userDetailsByLoginUseCase = App.appModule.userDetailsByLoginUseCase
                )
            }
        }
    }
}