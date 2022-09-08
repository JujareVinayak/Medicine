package com.vinayak.medicine.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.data.model.items.ProblemItem
import com.vinayak.medicine.data.model.response.Problems
import com.vinayak.medicine.domain.usecase.ProblemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 */
@HiltViewModel
class ProblemsViewModel @Inject constructor(private val problemsUseCase: ProblemsUseCase) :
    ViewModel() {

    private val _problems = MutableLiveData<Problems?>()
    val problems: LiveData<Problems?> get() = _problems

    fun getProblemsList() {
        viewModelScope.launch {
            problemsUseCase.getProblemsList()?.let {
                _problems.value = it
            } ?: run {
                _problems.value = null
            }
        }
    }

    fun insertProblem(problemItem: ProblemItem) {
        viewModelScope.launch {
            problemsUseCase.insertProblem(problemItem)
        }
    }

    fun insertMedicine(medicineItem: MedicineItem) {
        viewModelScope.launch {
            problemsUseCase.insertMedicine(medicineItem)
        }
    }

    fun getOfflineProblemsList() = problemsUseCase.getOfflineProblemsList()

    fun getOfflineMedicinesList() = problemsUseCase.getOfflineMedicinesList()
}