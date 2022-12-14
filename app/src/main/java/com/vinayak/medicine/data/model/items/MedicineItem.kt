package com.vinayak.medicine.data.model.items

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Data class to store medicine
 */
@Entity(tableName = "medicine_data_table")
@Parcelize
data class MedicineItem(
    @PrimaryKey
    @ColumnInfo(name = "medicine_id")
    var name: String,
    var dose: Int,
    var strength: String,
    var disease: String
) : Parcelable
