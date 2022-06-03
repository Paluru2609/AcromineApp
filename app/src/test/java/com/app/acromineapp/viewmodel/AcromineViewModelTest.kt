package com.acronymsapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.acronymsapp.TestCoroutineRule
import com.app.acromineapp.repository.AcromineRepository
import com.app.acromineapp.repository.remotesource.model.Acromine
import com.app.acromineapp.repository.remotesource.model.LongForm
import com.app.acromineapp.viewmodel.AcromineViewModel
import com.app.acromineapp.repository.remotesource.model.Result
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class AcromineViewModelTest {

    @get:Rule
    internal val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    private var repository = mockk<AcromineRepository>()
    private lateinit var viewModel: AcromineViewModel
    private lateinit var mockObserver: Observer<Result>

    @Before
    fun setUp() {
        viewModel = AcromineViewModel(repository)
        mockObserver = mockk()
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `get acronyms`() = coroutineTestRule.runBlockingTest {
        val longForm = LongForm("Federal Bureau of Investigation", 18, 1986, listOf())
        val acromine = Acromine("fbi", listOf(longForm))
        val responseList = listOf(acromine)

        coEvery { repository.getAcronyms(any()) } returns Result.Success(responseList)

        viewModel.getAcronyms("fbi").observeForever(mockObserver)

        verify(exactly = 1) { mockObserver.onChanged(any<Result>()) }
    }
}