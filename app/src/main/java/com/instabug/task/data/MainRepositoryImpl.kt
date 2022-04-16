package com.instabug.task.data

import com.instabug.task.base.NetworkHandler
import com.instabug.task.base.NetworkResult
import com.instabug.task.domain.MainRepository

class MainRepositoryImpl(private val apiService: ApiService) : MainRepository {
    override fun getWordsRemote(): NetworkResult<String> =
        NetworkHandler.call(api = apiService.instaBugUrl)
}