package com.vinayak.medicine.data.retrofit

import com.vinayak.medicine.data.model.response.Problems
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit endpoint service.
 */
interface ProblemsService {

    @GET("18a6495c-1204-43a2-b369-15a39e54f6de/")
    suspend fun getProblemsList(): Response<Problems?>
}