package com.example.redditsample.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.redditsample.databinding.ActivityMainItemBinding
import com.example.redditsample.viewholder.RedditItemViewHolder
import com.example.redditsample.viewmodel.MainItemViewModel

class RedditListAdapter : RecyclerView.Adapter<RedditItemViewHolder>() {

    private val itemViewModelList: MutableList<MainItemViewModel> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RedditItemViewHolder {
        val binding: ViewDataBinding?
        binding = ActivityMainItemBinding.inflate(LayoutInflater.from(p0.context), p0, false)

        return RedditItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemViewModelList.size
    }

    override fun onBindViewHolder(p0: RedditItemViewHolder, p1: Int) {
        p0.bind(itemViewModelList[p1])
    }

    fun setAdapter(itemList: MutableList<MainItemViewModel>) {
        itemViewModelList.clear()
        itemViewModelList.addAll(itemList)

        notifyDataSetChanged()
    }
}