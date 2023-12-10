package ru.chay.githubclient.ui.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.chay.githubclient.App
import ru.chay.githubclient.domain.model.UserDetails
import ru.chay.githubclient.domain.usecase.UserDetailsByLoginUseCase
import ru.chay.githubclient.ui.userslist.UsersListUiState
import ru.chay.githubclient.ui.userslist.UsersListViewModel
import kotlin.coroutines.cancellation.CancellationException

class UserDetailsViewModel(
    private val userDetailsByLoginUseCase: UserDetailsByLoginUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UserDetailsUiState>(UserDetailsUiState.Loading)
    val uiState: StateFlow<UserDetailsUiState> = _uiState
    private var loadingJob: Job? = null

    fun getUserDetails(username: String) {
        if(loadingJob?.isActive == true) loadingJob?.cancel()
        _uiState.value = UserDetailsUiState.Loading
        loadingJob = viewModelScope.launch {
            try {
                val user = userDetailsByLoginUseCase(username)
                _uiState.value = UserDetailsUiState.Success(user)
            } catch (e: CancellationException) {
                // ignore job cancel
            } catch (e: Exception) {
                _uiState.value = UserDetailsUiState.Error(e)
            }
        }
    }

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