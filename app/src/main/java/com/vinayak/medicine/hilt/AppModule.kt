package com.vinayak.medicine.hilt

import android.app.Application
import androidx.room.Room
import com.vinayak.medicine.data.repo.ProblemsRepositoryImpl
import com.vinayak.medicine.data.retrofit.ProblemsService
import com.vinayak.medicine.db.ProblemsDatabase
import com.vinayak.medicine.domain.repo.ProblemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideBaseUrl() =
        "https://run.mocky.io/v3/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val log = HttpLoggingInterceptor()
        log.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient
            .Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(log)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ProblemsService::class.java)

    @Provides
    @Singleton
    fun provideProblemsRepository(problemsRepositoryImpl: ProblemsRepositoryImpl): ProblemsRepository =
        problemsRepositoryImpl

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(app, ProblemsDatabase::class.java, "problems_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideProblemsDao(db: ProblemsDatabase) = db.problemsDao()
}