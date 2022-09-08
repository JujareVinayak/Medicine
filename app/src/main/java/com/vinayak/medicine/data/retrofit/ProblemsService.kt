package com.vinayak.medicine.data.retrofit

import com.vinayak.medicine.data.model.response.Problems
import retrofit2.Response
import retrofit2.http.GET

/**
 *
 */
interface ProblemsService {

    @GET("463fb60d-c959-4575-a781-123c62f21b93/")
    suspend fun getProblemsList(): Response<Problems?>
}