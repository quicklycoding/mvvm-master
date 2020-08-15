package com.quicklycoding.mvvm_master.base

interface BasicListener {

    fun toast(message: String)

    fun openActivity(activity: Class<*>)

}