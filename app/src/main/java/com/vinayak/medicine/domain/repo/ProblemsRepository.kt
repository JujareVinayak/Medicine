package com.vinayak.medicine.domain.repo

import com.vinayak.medicine.data.model.response.Problems

/**
 *
 */
interface ProblemsRepository {
    suspend fun getProblemsList(): Problems?
}