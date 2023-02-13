package com.meghamlabs.unittesting.data.remote.responses

data class ImageResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)