package com.instabug.task.ui

import com.instabug.task.ui.adapter.ItemWord

sealed class MainUiState {
    object Init : MainUiState()
    data class Loading(val isLoading: Boolean) : MainUiState()
    data class Failure(val message: String) : MainUiState()
    data class Success(val data: List<ItemWord>) : MainUiState()

}