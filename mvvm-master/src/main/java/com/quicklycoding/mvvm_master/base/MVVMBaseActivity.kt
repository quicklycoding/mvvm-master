package com.quicklycoding.mvvm_master.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.kodein.di.DIAware
import org.kodein.di.android.di

abstract class MVVMBaseActivity<VM : MVVMBaseViewModel, T : ViewDataBinding> : AppCompatActivity(),
    DIAware, BasicListener {

    lateinit var binding: T
    abstract val viewModel: VM

    abstract val bindingVariable: Int

    abstract val layout: Int

    override val di by di()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout)
        binding.lifecycleOwner = this
        binding.setVariable(bindingVariable, viewModel)
        viewModel.basicListener = this
        viewModel.view = binding.root
    }

    override fun toast(message: String) {
        Toast.makeText(this, message,  Toast.LENGTH_SHORT).show()
    }

}