package com.vinayak.medicine.db

import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.data.model.items.DiseaseItem
import javax.inject.Inject

class ProblemsOfflineRepository @Inject constructor(private val dao: ProblemsDAO) {

    val diseases = dao.getAllDiseases()

    val medicines = dao.getAllMedicines()

    suspend fun insertDisease(diseaseItem: DiseaseItem): Long {
        return dao.insertDisease(diseaseItem)
    }

    suspend fun insertMedicines(medicineItems: List<MedicineItem>) {
        return dao.insertMedicines(medicineItems)
    }
}