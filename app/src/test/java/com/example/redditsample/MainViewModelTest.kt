package com.example.redditsample

import android.content.Context
import com.example.redditsample.adapter.RedditListAdapter
import com.example.redditsample.manager.ConnectivityManager
import com.example.redditsample.manager.RetrofitManager
import com.example.redditsample.response.RedditResponse
import com.example.redditsample.service.RedditService
import com.example.redditsample.viewmodel.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainViewModelTest {

    val context = mockk<Context>()
    val redditListAdapter = mockk<RedditListAdapter>()
    val retrofitManager = mockk<RetrofitManager>()
    val anyClassMock = mockk<Any>()
    val redditService = mockk<RedditService>()
    val redditResponse = mockk<RedditResponse>()
    val compositeDisposable = mockk<CompositeDisposable>()
    val connectivityManager = mockk<ConnectivityManager>(relaxed = true)

    lateinit var subject: MainViewModel

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        MockKAnnotations.init( this )
        every { connectivityManager.isConnectedToInternet(any()) } returns true

        subject = MainViewModel(context, redditListAdapter, compositeDisposable, connectivityManager)
    }

    @Test
    fun connectedToInternet_addsCallToCompositeDisposable() {
        every { retrofitManager.getRetrofitBuilderService(any(), any()) } returns anyClassMock
        every { redditService.getTop(any()) } returns Single.just(redditResponse)
        every { compositeDisposable.add(any()) } returns true

        subject.loadData()

        verify { compositeDisposable.add(any()) }
    }
}
