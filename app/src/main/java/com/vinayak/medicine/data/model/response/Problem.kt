package com.vinayak.medicine.data.model.response

data class Problem(
    val disease: String,
    val medicines: List<Medicine>
)