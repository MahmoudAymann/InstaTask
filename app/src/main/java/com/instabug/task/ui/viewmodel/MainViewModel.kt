package com.instabug.task.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.instabug.task.base.ResourceWrap
import com.instabug.task.domain.WordsUseCase
import com.instabug.task.ui.MainUiState

class MainViewModel(private val app: Application, private val wordsUseCase: WordsUseCase) :
    AndroidViewModel(app) {
    //Handle UI states
    private val _uiState = MutableLiveData<MainUiState>(MainUiState.Init)
    val uiState: LiveData<MainUiState> = _uiState

    /*
    * Request data from server
    * */
    fun getData() {
        wordsUseCase.invoke(app.applicationContext) {
            emit(
                when (it) {
                    is ResourceWrap.Failure -> MainUiState.Failure(it.message)
                    is ResourceWrap.Progress -> MainUiState.Loading(it.loading)
                    is ResourceWrap.Success -> MainUiState.Success(it.data)
                }
            )
        }
    }

    private fun emit(state: MainUiState) {
        _uiState.postValue(state)
    }


}