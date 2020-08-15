package com.quicklycoding.mvvmmaster

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openActivity(MainActivity::class.java)
    }

    fun openActivity(activity: Class<*>) {
        Intent(this, activity).apply {
            startActivity(this)
        }
    }
}