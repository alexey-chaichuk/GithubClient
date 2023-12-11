package ru.chay.githubclient.ui.userdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.chay.githubclient.App
import ru.chay.githubclient.domain.model.Repository
import ru.chay.githubclient.domain.model.UserDetails
import ru.chay.githubclient.domain.usecase.ReposForUserUseCase
import ru.chay.githubclient.domain.usecase.UserDetailsByLoginUseCase
import kotlin.coroutines.cancellation.CancellationException

class UserDetailsViewModel(
    private val userDetailsByLoginUseCase: UserDetailsByLoginUseCase,
    private val reposForUserUseCase: ReposForUserUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UserDetailsUiState>(UserDetailsUiState.Loading)
    val uiState: StateFlow<UserDetailsUiState> = _uiState
    private var updateJob: Job? = null

    fun getUserDetails(username: String) {
        if(updateJob?.isActive == true) updateJob?.cancel()
        _uiState.value = UserDetailsUiState.Loading

        val handler = CoroutineExceptionHandler { _, exception ->
            when(exception) {
                is CancellationException -> {}
                else -> {
                    _uiState.value = UserDetailsUiState.Error(exception)
                }
            }
            Log.d("reposViewModel", "Got some exception while searching repos: $exception")
        }

        var user: UserDetails = UserDetails(
            name = "",
            fullName = "",
            repositoriesUrl = "",
            followersText = "",
            followers = 0
        )
        var repos: List<Repository> = emptyList()

        updateJob = viewModelScope.launch(handler) {
            val job1 = launch {
                user = userDetailsByLoginUseCase(username)
            }
            val job2 = launch {
                repos = reposForUserUseCase(username)
            }
            job1.join()
            job2.join()
            _uiState.value = UserDetailsUiState.Success(
                user,
                repos
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                UserDetailsViewModel(
                    userDetailsByLoginUseCase = App.appModule.userDetailsByLoginUseCase,
                    reposForUserUseCase = App.appModule.reposForUserUseCase
                )
            }
        }
    }
}