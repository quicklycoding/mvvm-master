package com.quicklycoding.mvvm_master.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.kodein.di.DIAware
import org.kodein.di.android.x.di

abstract class MVVMBaseFragment<VM : MVVMBaseViewModel, T : ViewDataBinding> : Fragment(), DIAware,
    NoInternetListener {

    lateinit var binding: T
    abstract val viewModel: VM

    override val di by di()

    abstract val bindingVariable: Int

    abstract val layout: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.setVariable(bindingVariable, viewModel)
        binding.lifecycleOwner = this

        //custom attributes
        viewModel.noInternetListener = this
        viewModel.context = requireContext()
        viewModel.view = binding.root
        viewModel.navController = findNavController()

        return binding.root
    }

    override fun noInternet(message: String) {
        Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
    }

}