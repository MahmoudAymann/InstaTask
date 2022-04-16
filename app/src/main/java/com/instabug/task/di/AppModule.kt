package com.instabug.task.di

import android.content.Context
import com.instabug.task.data.ApiService
import com.instabug.task.data.WordsRepositoryImpl
import com.instabug.task.db.DBHandler
import com.instabug.task.domain.WordsRepository
import com.instabug.task.domain.WordsUseCase

object AppModule {
    //manual provider class

    fun provideApiService(): ApiService = ApiService()

    fun provideRepo(apiService: ApiService, dbHandler: DBHandler): WordsRepository =
        WordsRepositoryImpl(apiService, dbHandler)

    fun provideInstaBugUseCase(repository: WordsRepository): WordsUseCase =
        WordsUseCase(repository)

    fun provideDBHandler(context: Context) = DBHandler.getInstance(context)
}