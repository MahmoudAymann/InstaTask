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
        text.trim().split(" ").forEach { word ->
            if (word.isNotEmpty()) {
                val key = regex.replace(word, "")
                resMap[key] = resMap[word]?.plus(1) ?: 1
            }
        }
        //setup words list
        resMap.onEachIndexed { index, entry ->
            listOfWords.add(
                ItemWord(
                    id = index, word = entry.key,
                    count = entry.value
                )
            )
        }
        Log.d("list", listOfWords.toString())
        return listOfWords
    }
}