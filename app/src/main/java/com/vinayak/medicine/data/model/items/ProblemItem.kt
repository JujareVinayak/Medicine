package com.vinayak.medicine.data.model.items

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 */
@Entity(tableName = "problem_data_table")
data class ProblemItem(
    @PrimaryKey
    @ColumnInfo(name = "problem_id")
    var disease: String,
    var medicineCount: String
)