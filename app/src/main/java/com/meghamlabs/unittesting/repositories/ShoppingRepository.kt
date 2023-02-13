package com.meghamlabs.unittesting.repositories

import androidx.lifecycle.LiveData
import com.meghamlabs.unittesting.data.local.ShoppingItem
import com.meghamlabs.unittesting.data.remote.responses.ImageResponse
import com.meghamlabs.unittesting.other.Resource
import retrofit2.Response
import retrofit2.http.Query

interface ShoppingRepository {


    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems() : LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>






}