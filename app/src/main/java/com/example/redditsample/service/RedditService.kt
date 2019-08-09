package com.example.redditsample.service

import com.example.redditsample.response.RedditResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {

    @GET("/top.json")
    fun getTop(@Query("limit") limit: String)
            : Single<RedditResponse>
}