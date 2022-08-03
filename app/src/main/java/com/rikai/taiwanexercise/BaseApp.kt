package com.rikai.taiwanexercise

import android.app.Application
import com.rikai.taiwanexercise.api.AppService
import com.rikai.taiwanexercise.databases.AppDatabase
import com.rikai.taiwanexercise.repositories.AppRepositoryImpl

class BaseApp : Application() {
    companion object {
        var baseApp: BaseApp? = null
    }

    lateinit var appService: AppService
    lateinit var appDatabase: AppDatabase
    lateinit var appRepositoryImpl: AppRepositoryImpl

    override fun onCreate() {
        super.onCreate()
        baseApp = this
        appService = AppService.getInstance()
        appDatabase = AppDatabase.getInstance(this)
        appRepositoryImpl = AppRepositoryImpl(
            appService,
            appDatabase
        )
    }
}