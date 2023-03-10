package com.meghamlabs.unittesting.reposiories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.meghamlabs.unittesting.data.local.ShoppingItem
import com.meghamlabs.unittesting.data.remote.responses.ImageResponse
import com.meghamlabs.unittesting.other.Resource
import com.meghamlabs.unittesting.repositories.ShoppingRepository

class FakeShoppingRepositories : ShoppingRepository {

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setshouldReturnNetworkError(value: Boolean) {

        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float{
            return shoppingItems.sumOf { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError){
            Resource.error("error",null)

        }else
            Resource.success(ImageResponse(listOf(),0,0))
    }
}