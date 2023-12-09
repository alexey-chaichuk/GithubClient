package ru.chay.githubclient.ui.userslist

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
import ru.chay.githubclient.domain.usecase.SearchUsersByNameUseCase
import kotlin.coroutines.cancellation.CancellationException

class UsersListViewModel(
    private val searchUsersByNameUseCase: SearchUsersByNameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UsersListUiState>(UsersListUiState.Success(emptyList()))
    val uiState: StateFlow<UsersListUiState> = _uiState
    private var searchJob: Job? = null

    fun onNewQuery(query: String) {
        if(query.isEmpty()) return
        if(searchJob?.isActive == true) searchJob?.cancel()
        _uiState.value = UsersListUiState.Searching
        searchJob = viewModelScope.launch {
            try {
                val users = searchUsersByNameUseCase(query)
                _uiState.value = UsersListUiState.Success(users)
            } catch (e: CancellationException) {
                // ignore job cancel
            } catch (e: Exception) {
                _uiState.value = UsersListUiState.Error(e)
            }
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

