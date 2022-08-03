package com.rikai.taiwanexercise.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rikai.taiwanexercise.models.User
import com.rikai.taiwanexercise.utils.AppUtils

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = createDatabase(context.applicationContext)
                    INSTANCE = instance
                }
                return instance
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                AppUtils.DATABASENAME
            ).fallbackToDestructiveMigration().build()
    }
}