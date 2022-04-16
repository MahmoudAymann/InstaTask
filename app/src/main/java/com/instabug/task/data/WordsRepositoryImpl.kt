package com.instabug.task.data

import com.instabug.task.base.NetworkHandler
import com.instabug.task.base.NetworkResult
import com.instabug.task.db.DBHandler
import com.instabug.task.domain.WordsRepository
import com.instabug.task.ui.adapter.ItemWord

//Consider adding Data Sources but this for simplicity
class WordsRepositoryImpl(private val apiService: ApiService, private val dbHandler: DBHandler) :
    WordsRepository {
    override fun getWordsRemote(): NetworkResult<String> =
        NetworkHandler.call(api = apiService.instaBugUrl)

    override fun getWordsLocal(): List<ItemWord> = dbHandler.getWords()

    override fun saveWordsLocal(listOfData: List<ItemWord>) = dbHandler.insertWords(listOfData)
}