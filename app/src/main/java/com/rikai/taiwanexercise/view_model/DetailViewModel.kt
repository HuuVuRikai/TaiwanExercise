package com.rikai.taiwanexercise.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rikai.taiwanexercise.models.Profile
import com.rikai.taiwanexercise.models.User
import com.rikai.taiwanexercise.repositories.AppRepositoryImpl
import com.rikai.taiwanexercise.utils.AppUtils
import com.rikai.taiwanexercise.utils.UseCaseResult
import com.rikai.taiwanexercise.view.DetailActivity
import kotlinx.coroutines.*
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class DetailViewModel(
    private val appRepositoryImpl: AppRepositoryImpl
) : ViewModel() , CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val showResult= MutableLiveData<Profile>()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getUser(login: String){
        showLoading.value = true
        viewModelScope.launch {
            try {
                var result = withContext(Dispatchers.IO){
                    appRepositoryImpl.get(login)
                }
                showLoading.value= false
                when (result) {
                    is UseCaseResult.Success -> {
                        showResult.value = result.data
                    }
                    is UseCaseResult.Error -> {
                        println(result.errorMessage)
                    }
                }
            } catch (networkError: IOException) {
                showLoading.value= false
            }

        }
    }
}