package com.rikai.taiwanexercise.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.rikai.taiwanexercise.api.AppService
import com.rikai.taiwanexercise.databases.AppDatabase
import com.rikai.taiwanexercise.models.Profile
import com.rikai.taiwanexercise.models.User
import com.rikai.taiwanexercise.utils.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepositoryImpl(
    private val appService: AppService,
    private val appDatabase: AppDatabase,
) : AppRepository {
    override suspend fun getUsersPagination(startPage: String, numPage: String): UseCaseResult<List<User>> {
        return try {
            val users = withContext(Dispatchers.IO) {
                appService.callUsersPaginationAsync(startPage, numPage).await()
            }
            withContext(Dispatchers.IO) {
                if(users.isNotEmpty()) insertAll(users)
            }
            UseCaseResult.Success(users)
        } catch (ex: Throwable) {
            UseCaseResult.Error(ex.message ?: "")
        }
    }

    override suspend fun getUsers(): UseCaseResult<List<User>> {
        return try {
            val users = withContext(Dispatchers.IO) {
                appService.callUsersAsync().await()
            }
            withContext(Dispatchers.IO) {
                if(users.isNotEmpty()) insertAll(users)
            }
            UseCaseResult.Success(users)
        } catch (ex: Throwable) {
            UseCaseResult.Error(ex.message ?: "")
        }
    }

    override suspend fun get(login: String): UseCaseResult<Profile> {
        return try {
            val result = withContext(Dispatchers.IO) {
                appService.callGetAsync(login).await()
            }
            UseCaseResult.Success(result)
        } catch (ex: Throwable) {
            UseCaseResult.Error(ex.message ?: "")
        }
    }

    override suspend fun clear() {
        appDatabase.userDao.clear()
    }

    override suspend fun insertAll(users: List<User>) {
        appDatabase.userDao.insertAll(users)
    }

    override val users: LiveData<List<User>>
        get() = appDatabase.userDao.getAll()
}