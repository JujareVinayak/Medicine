package com.vinayak.medicine.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.data.model.items.ProblemItem

@Database(entities = [ProblemItem::class, MedicineItem::class],version = 1)
abstract class  ProblemsDatabase : RoomDatabase() {

    abstract fun problemsDao() : ProblemsDAO
}