package com.vinayak.medicine.db

import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.data.model.items.DiseaseItem
import javax.inject.Inject

class ProblemsOfflineRepository @Inject constructor(private val dao: ProblemsDAO) {

    val problems = dao.getAllProblems()

    val medicines = dao.getAllMedicines()

    suspend fun insertProblem(diseaseItem: DiseaseItem): Long {
        return dao.insertProblem(diseaseItem)
    }

    suspend fun insertMedicines(medicineItems: List<MedicineItem>) {
        return dao.insertMedicines(medicineItems)
    }
}