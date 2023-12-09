package ru.chay.githubclient.ui.userslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.chay.githubclient.App
import ru.chay.githubclient.domain.usecase.SearchUsersByNameUseCase

class UsersListViewModel(
    private val searchUsersByNameUseCase: SearchUsersByNameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersListUiState.Success(emptyList()))
    val uiState: StateFlow<UsersListUiState> = _uiState

    init {
        viewModelScope.launch {
            val users = searchUsersByNameUseCase("")
            _uiState.value = UsersListUiState.Success(users)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                UsersListViewModel(
                    searchUsersByNameUseCase = App.appModule.searchUsersByNameUseCase
                )
            }
        }
    }
}

