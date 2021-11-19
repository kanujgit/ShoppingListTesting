package com.androiddevs.shoppinglisttestingyt.repositories

import androidx.lifecycle.LiveData
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingDao
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItem
import com.androiddevs.shoppinglisttestingyt.data.remote.PixabayAPI
import com.androiddevs.shoppinglisttestingyt.data.remote.resposne.ImageResponse
import com.androiddevs.shoppinglisttestingyt.others.Resource
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val dao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) : ShoppingRepo {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        dao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        dao.deleteShoppingItem(shoppingItem)
    }

    override fun observeShoppingItem(): LiveData<List<ShoppingItem>> {
        return dao.observeAllShoppingItems()
    }


    override fun observeTotalPrice(): LiveData<Float> {
        return dao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Unknown Error", null)
            } else {
                return Resource.error("Unknown error", null)
            }
        } catch (e: Exception) {
            Resource.error("Something went wrong", null)
        }

    }
}