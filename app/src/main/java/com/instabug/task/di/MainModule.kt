package com.instabug.task.di

import com.instabug.task.data.ApiService
import com.instabug.task.data.MainRepositoryImpl
import com.instabug.task.domain.MainRepository
import com.instabug.task.domain.WordsUseCase

class MainModule {
    //manual provider class

    fun provideApiService(): ApiService = ApiService()

    fun provideRepo(apiService: ApiService): MainRepository = MainRepositoryImpl(apiService)

    fun provideInstaBugUseCase(repository: MainRepository): WordsUseCase =
        WordsUseCase(repository)
}