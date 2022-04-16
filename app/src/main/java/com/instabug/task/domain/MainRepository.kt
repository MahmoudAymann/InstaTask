package com.instabug.task.domain

import com.instabug.task.base.NetworkResult

interface MainRepository {
    fun getWordsRemote(): NetworkResult<String>
}