package com.instabug.task.domain

import android.util.Log
import com.instabug.task.ui.adapter.ItemWord
import org.jsoup.Jsoup

class WordsMapper {
    private val regex = "[^A-Za-z]".toRegex()

    fun mapToListOfWords(body: String): List<ItemWord> {
        val text = Jsoup.parse(body).body().text()
        val listOfWords = arrayListOf<ItemWord>()
        val resMap = mutableMapOf<String, Int>()
        text.trim().split(" ").forEach {
            if (it.isNotEmpty()) {
                val value = regex.replace(it, "")
                resMap[value] = resMap[it]?.plus(1) ?: 1
            }
        }
        resMap.forEach {
            listOfWords.add(ItemWord(id = it.key.indexOf(it.key), word = it.key, count = it.value))
        }
        Log.e("list", listOfWords.toString())
        return listOfWords
    }
}