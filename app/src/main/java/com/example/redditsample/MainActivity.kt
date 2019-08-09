package com.example.redditsample


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.redditsample.adapter.RedditListAdapter
import com.example.redditsample.databinding.ActivityMainBinding
import com.example.redditsample.viewmodel.MainViewModel
import io.reactivex.disposables.CompositeDisposable
import com.example.redditsample.manager.ConnectivityManager

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var adapter: RedditListAdapter
    lateinit var viewModel: MainViewModel
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = RedditListAdapter()
        compositeDisposable = CompositeDisposable()
        connectivityManager = ConnectivityManager()
        viewModel = MainViewModel(this, adapter, compositeDisposable, connectivityManager)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel


        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter

        binding.executePendingBindings()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }
}
