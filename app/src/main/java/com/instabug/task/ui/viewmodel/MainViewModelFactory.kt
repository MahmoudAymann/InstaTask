package com.instabug.task.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.instabug.task.di.MainModule
import com.instabug.task.domain.WordsUseCase

class MainViewModelFactory private constructor(
    private val application: Application,
    mainModule: MainModule
) : ViewModelProvider.NewInstanceFactory() {
    private val getUseCase: WordsUseCase =
        mainModule.provideInstaBugUseCase(mainModule.provideRepo(mainModule.provideApiService()))

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (MainViewModel::class.java.isAssignableFrom(modelClass)) {
            try {
                modelClass.getConstructor(
                    Application::class.java, WordsUseCase::class.java
                ).newInstance(application, getUseCase)
            } catch (e: Exception) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        } else super.create(modelClass)
    }

    companion object {
        private var sInstance: MainViewModelFactory? = null
        fun getInstance(
            application: Application,
            module: MainModule
        ): MainViewModelFactory {
            if (sInstance == null) {
                sInstance = MainViewModelFactory(application, module)
            }
            return sInstance as MainViewModelFactory
        }
    }
}