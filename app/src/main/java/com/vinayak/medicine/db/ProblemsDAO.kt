package com.vinayak.medicine.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.data.model.items.DiseaseItem


@Dao
interface ProblemsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProblem(diseaseItem: DiseaseItem): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMedicines(medicineItems: List<MedicineItem>)

    @Query("SELECT * FROM problem_data_table")
    fun getAllProblems(): LiveData<List<DiseaseItem>>

    @Query("SELECT * FROM medicine_data_table")
    fun getAllMedicines(): LiveData<List<MedicineItem>>

}