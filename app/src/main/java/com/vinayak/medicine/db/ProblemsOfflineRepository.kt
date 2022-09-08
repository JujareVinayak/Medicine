package com.vinayak.medicine.db

import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.data.model.items.ProblemItem
import javax.inject.Inject

class ProblemsOfflineRepository @Inject constructor(private val dao: ProblemsDAO) {

    val problems = dao.getAllProblems()

    val medicines = dao.getAllMedicines()

    suspend fun insertProblem(problemItem: ProblemItem): Long {
        return dao.insertProblem(problemItem)
    }

    suspend fun insertMedicine(medicineItem: MedicineItem): Long {
        return dao.insertMedicine(medicineItem)
    }
}