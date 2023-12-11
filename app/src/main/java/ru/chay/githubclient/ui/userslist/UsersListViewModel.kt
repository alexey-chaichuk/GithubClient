package ru.chay.githubclient.ui.userslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ru.chay.githubclient.App
import ru.chay.githubclient.domain.model.User
import ru.chay.githubclient.domain.usecase.SearchUsersByNameUseCase
import ru.chay.githubclient.domain.usecase.UpdateUserDetailsUseCase
import kotlin.coroutines.cancellation.CancellationException

class UsersListViewModel(
    private val searchUsersByNameUseCase: SearchUsersByNameUseCase,
    private val updateUserDetailsUseCase: UpdateUserDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UsersListUiState>(UsersListUiState.Success(emptyList()))
    val uiState: StateFlow<UsersListUiState> = _uiState

    private val _events = Channel<UsersListUiEvent>()
    val events = _events.receiveAsFlow()

    private var searchJob: Job? = null

    fun onNewQuery(query: String) {
        if(query.isEmpty()) return
        if(searchJob?.isActive == true) searchJob?.cancel()
        _uiState.value = UsersListUiState.Searching

        val handler = CoroutineExceptionHandler { _, exception ->
            when(exception) {
                is CancellationException -> {}
                else -> {
                    _uiState.value = UsersListUiState.Error(exception)
                }
            }
            Log.d("mainViewModel", "Got some exception while searching users: $exception")
        }

        searchJob = viewModelScope.launch(handler) {
            val users = searchUsersByNameUseCase(query)
            _uiState.value = UsersListUiState.Success(users)
//            users.map { user ->
//                launch {
//                    updateUserDetailsUseCase(user)
//                }
//            }.joinAll()
//            _uiState.value = UsersListUiState.Success(users)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                UsersListViewModel(
                    searchUsersByNameUseCase = App.appModule.searchUsersByNameUseCase,
                    updateUserDetailsUseCase = App.appModule.updateUserDetailsUseCase
                )
            }
        }
    }
}

