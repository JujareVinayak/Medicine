package com.vinayak.medicine.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockito_kotlin.whenever
import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.data.model.items.ProblemItem
import com.vinayak.medicine.domain.usecase.ProblemsUseCase
import com.vinayak.medicine.presentation.BaseTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

/**
 * Test class for [ProblemsViewModel]
 */
class ProblemsViewModelTest : BaseTest() {

    private lateinit var problemsViewModel: ProblemsViewModel

    @Mock
    private lateinit var problemsUseCase: ProblemsUseCase

    @Before
    override fun setup() {
        super.setup()
        problemsViewModel = ProblemsViewModel(problemsUseCase)
    }

    @Test
    fun `test getOfflineProblemsList`() {
        whenever(problemsUseCase.getOfflineProblemsList()).thenReturn(
            MutableLiveData(
                listOf(
                    ProblemItem("Diabetes", "1")
                )
            )
        )
        val result = problemsViewModel.getOfflineProblemsList().value
        assertNotNull(result)
        assertEquals(1, result?.size)
        assertEquals("Diabetes", result?.get(0)?.disease)
        assertEquals("1", result?.get(0)?.medicineCount)
    }

    @Test
    fun `test getOfflineMedicinesList`() {
        whenever(problemsUseCase.getOfflineMedicinesList()).thenReturn(
            MutableLiveData(
                listOf(
                    MedicineItem("Asprin", "1", "5mg", "Diabetes")
                )
            )
        )
        val result = problemsViewModel.getOfflineMedicinesList().value
        assertNotNull(result)
        assertEquals(1, result?.size)
        assertEquals("Asprin", result?.get(0)?.name)
        assertEquals("1", result?.get(0)?.dose)
        assertEquals("5mg", result?.get(0)?.strength)
        assertEquals("Diabetes", result?.get(0)?.disease)
    }
}