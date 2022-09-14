package com.vinayak.medicine.domain.usecase

import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.data.model.items.DiseaseItem
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

    suspend fun insertDisease(diseaseItem: DiseaseItem): Long {
        return problemsOfflineRepository.insertDisease(diseaseItem)
    }

    suspend fun insertMedicines(medicineItems: List<MedicineItem>) {
        return problemsOfflineRepository.insertMedicines(medicineItems)
    }

    fun getOfflineDiseasesList() = problemsOfflineRepository.diseases

    fun getOfflineMedicinesList() = problemsOfflineRepository.medicines

}