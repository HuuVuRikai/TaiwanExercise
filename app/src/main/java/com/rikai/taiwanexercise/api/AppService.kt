package com.rikai.taiwanexercise.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rikai.taiwanexercise.models.Profile
import com.rikai.taiwanexercise.models.User
import com.rikai.taiwanexercise.utils.AppUtils
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface AppService {
    @GET("users")
    fun callUsersAsync(): Deferred<List<User>>

    @GET("users/{login}")
    fun callGetAsync(
        @Path("login") login: String
    ): Deferred<Profile>

    companion object {
        var appService: AppService? = null

        fun getInstance() : AppService {
            if (appService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(AppUtils.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()

                appService = retrofit.create(AppService::class.java)
            }
            return appService!!
        }
    }

}