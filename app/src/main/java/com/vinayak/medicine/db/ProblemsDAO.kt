package com.vinayak.medicine.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.data.model.items.ProblemItem


@Dao
interface ProblemsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProblems(problemItems: List<ProblemItem>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProblem(problemItem: ProblemItem): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMedicines(medicineItems: List<MedicineItem>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMedicine(medicineItem: MedicineItem): Long

    @Query("SELECT * FROM problem_data_table")
    fun getAllProblems(): LiveData<List<ProblemItem>>

    @Query("SELECT * FROM medicine_data_table")
    fun getAllMedicines(): LiveData<List<MedicineItem>>

}