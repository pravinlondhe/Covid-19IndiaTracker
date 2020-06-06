package com.prl.android.covid19india.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.prl.android.covid19india.data.model.country.Covid19DataResponse
import com.prl.android.covid19india.data.model.statewise.StateCovid19DataResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Covid19ApiService {
    @GET("data.json")
    fun getCovid19Data(): Deferred<Covid19DataResponse>

    @GET("v2/state_district_wise.json")
    fun getStateWiseData(): Deferred<StateCovid19DataResponse>

    companion object{
        operator fun invoke() : Covid19ApiService {
            val interceptor : Interceptor = Interceptor { chain ->
                println(chain.request().url().toString())
                println(chain.request().body().toString())
                println(chain.request().headers().toString())
                return@Interceptor chain.proceed(chain.request())
            }

            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.covid19india.org/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Covid19ApiService::class.java)
        }
    }
}