package com.example.redditsample.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.content.ContentValues.TAG
import android.content.Context
import android.databinding.BaseObservable
import android.util.Log
import com.example.redditsample.adapter.RedditListAdapter
import com.example.redditsample.response.RedditResponse
import com.example.redditsample.service.RedditService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel constructor(private val context: Context, val adapter: RedditListAdapter) : BaseObservable() {

    private val itemViewModelList: MutableList<MainItemViewModel> = ArrayList()
    private val BASE_URL = "https://www.reddit.com"
    private var compositeDisposable: CompositeDisposable? = null

    private fun parseJsonResponse(response: RedditResponse) {
        itemViewModelList.clear()
        for (resp in response.childResponse?.data!!) {
            itemViewModelList.add(
                MainItemViewModel(
                    context,
                    resp.data?.tumbnailUrl!!,
                    resp.data.title!!,
                    "https://www.reddit.com/" + resp.data.permaLink!!
                )
            )
        }
        adapter.setAdapter(itemViewModelList)
    }

    fun loadData() {

        val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(RedditService::class.java)

        compositeDisposable = CompositeDisposable()
        compositeDisposable?.add(
            requestInterface.getTop("10")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseJsonResponse, this::handleError)
        )

    }

    private fun handleError(error: Throwable) {
        Log.d(TAG, "Parsing Error : " + error.localizedMessage)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        compositeDisposable?.clear()
    }
}