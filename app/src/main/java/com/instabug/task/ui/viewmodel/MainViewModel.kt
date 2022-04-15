package com.instabug.task.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.instabug.task.ui.MainUiState

class MainViewModel() : ViewModel(){
    //Handle UI states
    private val _uiState = MutableLiveData<MainUiState>(MainUiState.Init)
    val uiState: LiveData<MainUiState> = _uiState

    /*
    * Request data from server
    * */
    fun getData(){
        _uiState.value = MainUiState.Loading(true)
    }


}