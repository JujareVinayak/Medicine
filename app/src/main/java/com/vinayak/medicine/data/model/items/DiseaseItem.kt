package com.vinayak.medicine.data.model.items

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class to store disease.
 */
@Entity(tableName = "disease_data_table")
data class DiseaseItem(
    @PrimaryKey
    @ColumnInfo(name = "disease_id")
    var disease: String,
    var medicineCount: Int
)