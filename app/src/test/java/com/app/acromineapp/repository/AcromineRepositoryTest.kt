package com.app.acromineapp.repository

import com.app.acromineapp.repository.remotesource.AcromineRemoteSource
import com.app.acromineapp.repository.remotesource.model.Acromine
import com.app.acromineapp.repository.remotesource.model.LongForm
import com.app.acromineapp.repository.remotesource.model.Result
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class AcromineRepositoryTest {
    private var remoteSource = mockk<AcromineRemoteSource>()
    private lateinit var repository: AcromineRepository

    @Before
    fun setUp() {
        repository = AcronymRepositoryImpl(remoteSource)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `fetch meanings success`() = runBlockingTest {
        val longForm = LongForm("Federal Bureau of Investigation", 18, 1986, listOf())
        val acromine = Acromine("fbi", listOf(longForm))
        val responseList = listOf(acromine)

        coEvery { remoteSource.getAcronyms(any())} returns Response.success(responseList)

        val expectedResult: Result = repository.getAcronyms("fbi")
        assertTrue(expectedResult is Result.Success)

        val expectedSuccessResult = expectedResult as Result.Success
        assertEquals(expectedSuccessResult.acromineList, responseList)
    }

    @Test
    fun `fetch meanings error`() = runBlockingTest {
        coEvery { remoteSource.getAcronyms(any())} returns
                Response.error(500, ResponseBody.create(null, "Server Error"))

        val expectedResult: Result = repository.getAcronyms("fbi")
        assertTrue(expectedResult is Result.Error)

        val expectedErrorResult = expectedResult as Result.Error
        assertEquals(expectedErrorResult.exception.message, "Server Error")
    }
}