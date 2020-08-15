package com.quicklycoding.mvvm_master.base

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.quicklycoding.mvvm_master.util.ApiException
import com.quicklycoding.mvvm_master.util.NoInternetException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class MVVMBaseViewModel : ViewModel() {

    lateinit var basicListener: BasicListener
    lateinit var view: View
    lateinit var navController: NavController
    lateinit var context: Context

    //Store API calls and Asynchronous call in this JOB
    private var job: Job? = null

    //Below two variables to handle progressBar visibility
    private val _progressVisibility = MutableLiveData(View.GONE)
    val progressVisibility: LiveData<Int> = _progressVisibility

    //Show Progress
    fun showProgress() {
        _progressVisibility.value = View.VISIBLE
    }

    //Hide Progress
    fun hideProgress() {
        _progressVisibility.value = View.GONE
    }

    // Show Toast
    fun toast(message: String) = basicListener.toast(message)

    // open Activity
    fun openActivity(activity: Class<*>) = basicListener.openActivity(activity)

    /*
    * This method only for API Calls
    * Using this method don't worry about NoInternet, ApiErrors
    * This method will handle everything for you...
    * */
    fun coroutineAPICall(work: suspend (() -> Unit)) {
        job = viewModelScope.launch {
            showProgress()
            try {
               return@launch work()
            } catch (ex: ApiException) {
                toast(ex.message!!)
            } catch (ex: NoInternetException) {
                toast(ex.message!!)
            } catch (ex: Exception) {
                Log.d("BaseViewModel", "CoroutineAPICall: ${ex.message}")
            }
            hideProgress()
        }
    }

    //Clear Jobs
    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }

}