package com.instabug.task.domain

import com.instabug.task.base.NetworkResult
import com.instabug.task.ui.adapter.ItemWord

interface WordsRepository {
    fun getWordsRemote(): NetworkResult<String>
    fun getWordsLocal(): List<ItemWord>

    fun saveWordsLocal(listOfData: List<ItemWord>): Boolean
}