package com.vinayak.medicine.data.repo

import com.vinayak.medicine.data.model.response.Problems
import com.vinayak.medicine.data.source.ProblemsDataSource
import com.vinayak.medicine.domain.repo.ProblemsRepository
import javax.inject.Inject

/**
 *
 */
class ProblemsRepositoryImpl @Inject constructor(private val problemsDataSource: ProblemsDataSource) :
    ProblemsRepository {

    override suspend fun getProblemsList(): Problems? {
        problemsDataSource.getProblemsList()?.let {
            return it
        } ?: run {
            return null
        }
    }
}