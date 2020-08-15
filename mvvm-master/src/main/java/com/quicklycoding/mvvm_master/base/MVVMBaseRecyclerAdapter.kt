package com.quicklycoding.mvvm_master.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class MVVMBaseRecyclerAdapter<T : ViewDataBinding>(private val list: List<Any>) :
    RecyclerView.Adapter<MVVMBaseRecyclerAdapter.MyViewHolder>() {

    lateinit var binding: T
    abstract val layout: Int

    class MyViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding =
            DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), layout, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size

}