package com.devstresk.devstreakapp.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devstresk.devstreakapp.data.local.UserPreferences
import com.devstresk.devstreakapp.domain.model.Contest
import com.devstresk.devstreakapp.domain.model.UserStatus
import com.devstresk.devstreakapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = mutableStateOf<UiState>(UiState.Loading)
    val uiState: State<UiState> = _uiState


    private val _savedHandle = mutableStateOf<String?>(null)
    val savedHandle: State<String?> = _savedHandle

    init {
        checkSavedUser()
    }

    private fun checkSavedUser() {
        viewModelScope.launch {
            userPreferences.userHandle.collect { handle ->
                _savedHandle.value = handle
                if (handle != null) {
                    fetchUserProfile(handle)
                } else {
                    _uiState.value = UiState.Error("Please sign in first ً")
                }
            }
        }
    }

    fun saveNewHandle(handle: String, shouldRemember: Boolean) {
        viewModelScope.launch {
            if (shouldRemember) {
                userPreferences.saveUserHandle(handle)
            } else {
                _savedHandle.value = handle
                fetchUserProfile(handle)
            }
        }
    }

    fun fetchUserProfile(handle: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                combine(
                    userRepository.getUserInfo(handle),
                    userRepository.getContests()
                ) { userStatus, contests ->
                    if (userStatus != null) {
                        UiState.Success(userStatus, contests)
                    } else {
                        UiState.Error(" No user data was found ")
                    }
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("  Server connection error : ${e.localizedMessage}")
            }
        }
    }
    fun clearSavedHandle() {
        viewModelScope.launch {
            userPreferences.saveUserHandle(null)
            _savedHandle.value = null 
            _uiState.value = UiState.Loading
        }
    }
    private val _isDarkMode = mutableStateOf(true)
    val isDarkMode: State<Boolean> = _isDarkMode

    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
    }
}


sealed interface UiState {
    object Loading : UiState
    data class Success(val data: UserStatus, val contests: List<Contest>) : UiState
    data class Error(val message: String) : UiState
}
