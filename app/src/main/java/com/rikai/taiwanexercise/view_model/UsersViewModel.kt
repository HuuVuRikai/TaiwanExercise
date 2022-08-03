package com.rikai.taiwanexercise.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rikai.taiwanexercise.models.User
import com.rikai.taiwanexercise.repositories.AppRepositoryImpl
import com.rikai.taiwanexercise.utils.UseCaseResult
import kotlinx.coroutines.*
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class UsersViewModel(
    private val appRepositoryImpl: AppRepositoryImpl
) : ViewModel() , CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val showResult = MutableLiveData<Boolean>()
    val usersResult = MutableLiveData<List<User>>()
//    val users = appRepositoryImpl.users
    val users : MutableList<User> = mutableListOf()
    var startPage: Int = 1
    var numPage: Int = 20

    init {
        getUsersPagination()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getUsersPagination() {
        showLoading.value = true
        viewModelScope.launch {
            try {
                var result = withContext(Dispatchers.IO){
                    appRepositoryImpl.getUsersPagination(startPage.toString(), numPage.toString())
                }
                showLoading.value= false
                when (result) {
                    is UseCaseResult.Success -> {
                        if(result.data.isNotEmpty()){
                            withContext(Dispatchers.IO){
                                appRepositoryImpl.insertAll(result.data)
                            }
                            users.addAll(result.data)
                            startPage += result.data.size
                            showResult.value = true
                        }
                    }
                    is UseCaseResult.Error -> {
                        showResult.value = false
                    }
                }

            } catch (networkError: IOException) {
                showLoading.value= false
                showResult.value = false
            }

        }
    }

    fun getUsers(){
        showLoading.value = true
        viewModelScope.launch {
            try {
                var result = withContext(Dispatchers.IO){
                    appRepositoryImpl.getUsers()
                }
                showLoading.value= false
                when (result) {
                    is UseCaseResult.Success -> {
                        showResult.value = true
                        if(result.data.isNotEmpty()){
                            withContext(Dispatchers.IO){
                                appRepositoryImpl.insertAll(result.data)
                            }
                        }
                    }
                    is UseCaseResult.Error -> {
                        showResult.value = false
                    }
                }

            } catch (networkError: IOException) {
                showLoading.value= false
                showResult.value = false
            }

        }
    }

    fun clearUsers() {
        viewModelScope.launch {
            appRepositoryImpl.clear()
        }
    }
}