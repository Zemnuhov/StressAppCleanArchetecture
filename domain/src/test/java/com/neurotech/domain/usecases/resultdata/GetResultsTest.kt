package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import java.util.*

class GetResultsTest {
    @Test
    fun test1() = runTest {
        val repository = mock<ResultDataRepository>()
        val testData = flow {
            emit(listOf(ResultDomainModel(Date(), 10, 2000, 1)))
        }
        Mockito.`when`(repository.getResults()).thenReturn(testData)

        val useCase = GetResults(repository = repository)
        val actual = useCase.invoke()
        val expected = listOf(ResultDomainModel(Date(), 10, 2000, 1))

        actual.collect{
            Assertions.assertArrayEquals(it.toTypedArray(), expected.toTypedArray())
        }


    }


}