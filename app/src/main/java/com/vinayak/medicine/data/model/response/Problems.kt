package com.vinayak.medicine.data.model.response

import com.vinayak.medicine.data.model.items.MedicineItem
import com.vinayak.medicine.data.model.items.DiseaseItem

data class Problems(
    val medicines: List<MedicineItem>,
    val diseases: List<DiseaseItem>
)