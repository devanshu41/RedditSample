package com.example.redditsample.response

import com.google.gson.annotations.SerializedName

data class RedditResponse(
    @SerializedName("data")
    var childResponse: RedditChildResponse?
)

data class RedditChildResponse(
    @SerializedName("children")
    val data: List<ChildData>?
)

data class ChildData(
    @SerializedName("data")
    val data: Title?
)

data class Title(
    @SerializedName("title")
    val title: String?,

    @SerializedName("thumbnail")
    val tumbnailUrl: String?,

    @SerializedName("permalink")
    val permaLink: String?
)