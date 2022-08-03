package com.rikai.taiwanexercise.repositories

import androidx.lifecycle.LiveData
import com.rikai.taiwanexercise.models.Profile
import com.rikai.taiwanexercise.models.User
import com.rikai.taiwanexercise.utils.UseCaseResult

interface AppRepository {
    suspend fun getUsers(): UseCaseResult<List<User>>
    suspend fun get(login: String): UseCaseResult<Profile>
    suspend fun clear()
    suspend fun insertAll(users: List<User>)
    val users: LiveData<List<User>>
}