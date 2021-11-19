package com.androiddevs.shoppinglisttestingyt.data.remote.resposne

data class ImageResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
)
