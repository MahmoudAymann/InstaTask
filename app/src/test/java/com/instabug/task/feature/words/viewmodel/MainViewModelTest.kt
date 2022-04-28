package com.instabug.task.feature.words.viewmodel

import android.app.Application
import com.instabug.task.base.ResourceWrap
import com.instabug.task.domain.WordsUseCase
import com.instabug.task.ui.adapter.ItemWord
import com.instabug.task.ui.viewmodel.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class MainViewModelTest {

    lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var applicationMock: Application

    @Mock
    lateinit var useCase: WordsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        applicationMock = Mockito.mock(Application::class.java)
        viewModel = MainViewModel(app = applicationMock, wordsUseCase = useCase)
    }

    @Test
    fun `when successfully get data`() {
        val data: List<ItemWord> = listOf()
        val expected = ResourceWrap.Success(data)
        applicationMock = Mockito.mock(Application::class.java)
        whenever(useCase.invoke(applicationMock, any())).thenAnswer {
            val result = it.getArgument<((ResourceWrap<List<ItemWord>>) -> Unit)>(1)
            result.invoke(ResourceWrap.success(data))
        }
        useCase.invoke(applicationMock) {
            assertEquals(expected, it)
        }
    }

}