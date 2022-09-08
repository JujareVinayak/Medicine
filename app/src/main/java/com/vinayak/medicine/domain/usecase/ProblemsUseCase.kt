package com.vinayak.medicine.domain.usecase

import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.data.model.items.ProblemItem
import com.vinayak.medicine.db.ProblemsOfflineRepository
import com.vinayak.medicine.domain.repo.ProblemsRepository
import javax.inject.Inject

/**
 *
 */
class ProblemsUseCase @Inject constructor(
    private val problemsRepository: ProblemsRepository,
    private val problemsOfflineRepository: ProblemsOfflineRepository
) {
    suspend fun getProblemsList() = problemsRepository.getProblemsList()

    suspend fun insertProblem(problemItem: ProblemItem): Long {
        return problemsOfflineRepository.insertProblem(problemItem)
    }

    suspend fun insertMedicine(medicineItem: MedicineItem): Long {
        return problemsOfflineRepository.insertMedicine(medicineItem)
    }

    fun getOfflineProblemsList() = problemsOfflineRepository.problems

    fun getOfflineMedicinesList() = problemsOfflineRepository.medicines

}