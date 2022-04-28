package com.instabug.task.feature.words.repo

import com.instabug.task.base.NetworkResult
import com.instabug.task.data.ApiService
import com.instabug.task.data.WordsRepositoryImpl
import com.instabug.task.db.DBHandler
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class MainRepositoryImplTest {

    lateinit var repo: WordsRepositoryImpl

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var dbHandler: DBHandler

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repo = WordsRepositoryImpl(apiService = apiService, dbHandler = dbHandler)
    }

    @Test
    fun `test remote success`() {
        val expected = NetworkResult.Success("")
        whenever(apiService.instaBugUrl).thenReturn("")
        whenever(repo.getWordsRemote()).thenReturn(expected)
        val actual = repo.getWordsRemote()

        assertEquals(expected, actual)
    }
}