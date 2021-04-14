package com.prl.android.covid19india.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.prl.android.covid19india.data.model.country.Covid19DataResponse
import com.prl.android.covid19india.data.model.statewise.StateCovid19DataResponse
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Covid19ApiService {
    @GET("/data.json")
    fun getCovid19DataAsync(): Deferred<Covid19DataResponse>

    @GET("/v2/state_district_wise.json")
    fun getStateWiseDataAsync(): Deferred<StateCovid19DataResponse>

    companion object {
        private const val BASE_URL = "https://api.covid19india.org"
        operator fun invoke(): Covid19ApiService {
            /*val interceptor: Interceptor = Interceptor { chain ->
                println("Url:${chain.request().url}")
                println("Body:${chain.request().body}")
                println("Headers:${chain.request().headers}")
                return@Interceptor chain.proceed(chain.request())
            }*/

            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Covid19ApiService::class.java)
        }
    }
}