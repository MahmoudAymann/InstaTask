package com.instabug.task.ui

sealed class MainUiState {
    object Init : MainUiState()
    data class Loading(val isLoading: Boolean) : MainUiState()

}