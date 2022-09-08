package com.vinayak.medicine.data.source

import com.vinayak.medicine.data.model.response.Problems
import com.vinayak.medicine.data.retrofit.ProblemsService
import javax.inject.Inject

/**
 *
 */
class ProblemsDataSource @Inject constructor(private val problemsService: ProblemsService) {
    suspend fun getProblemsList(): Problems? {
        val response = problemsService.getProblemsList()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}