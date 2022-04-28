package com.instabug.task.domain

import android.content.Context
import com.instabug.task.base.NetworkHandler
import com.instabug.task.base.NetworkResult
import com.instabug.task.base.ResourceWrap
import com.instabug.task.ui.adapter.ItemWord
import java.util.concurrent.Executors

open class WordsUseCase(private val repository: WordsRepository) {
    companion object {
        const val TAG = "WordsUseCase"
    }

    fun invoke(context: Context, result: (ResourceWrap<List<ItemWord>>) -> Unit) {
        result.invoke(ResourceWrap.loading(true))
        Executors.newSingleThreadScheduledExecutor().let {
            it.execute {
                if (NetworkHandler.isNetworkAvailable(context)) { //Online
                    when (val res = repository.getWordsRemote()) {
                        is NetworkResult.Failure -> {
                            result.invoke(ResourceWrap.failure(res.message))
                            it.shutdown()
                            result.invoke(ResourceWrap.loading(false))
                        }
                        is NetworkResult.Success -> {
                            val list = WordsMapper().mapToListOfWords(res.data)
                            if (repository.saveWordsLocal(list)) {
                                val local = repository.getWordsLocal()
                                result.invoke(ResourceWrap.success(local))
                            } else
                                result.invoke(ResourceWrap.success(emptyList()))
                            result.invoke(ResourceWrap.loading(false))
                            it.shutdown()
                        }
                    }
                } else { //Offline
                    result.invoke(ResourceWrap.success(repository.getWordsLocal()))
                    result.invoke(ResourceWrap.loading(false))
                }

            }
        }
    }


}