package com.example.redditsample.viewholder

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.example.redditsample.BR

class RedditItemViewHolder (val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
    fun <VM> bind(viewModel: VM) {
        binding.setVariable(BR.itemViewModel, viewModel)
        binding.executePendingBindings()
    }
}