package com.androiddevs.shoppinglisttestingyt.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItem
import com.androiddevs.shoppinglisttestingyt.data.remote.resposne.ImageResponse
import com.androiddevs.shoppinglisttestingyt.others.Event
import com.androiddevs.shoppinglisttestingyt.others.Resource
import com.androiddevs.shoppinglisttestingyt.repositories.DefaultRepository
import com.androiddevs.shoppinglisttestingyt.repositories.ShoppingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class ShoppingViewModel @Inject constructor
    (private val repository:ShoppingRepo) : ViewModel() {
    val shoppingItems = repository.observeShoppingItem()

    val totalPrice = repository.observeTotalPrice()

    private val _image = MutableLiveData<Event<Resource<ImageResponse>>>()
    val image: LiveData<Event<Resource<ImageResponse>>> = _image

    private val _currImageUrl = MutableLiveData<String>()
    val currImageUrl: LiveData<String> = _currImageUrl


    private val _insertShoppingItem = MutableLiveData<Event<Resource<ImageResponse>>>()
    val insertShoppingItem: LiveData<Event<Resource<ImageResponse>>> = _insertShoppingItem

    fun setCurrentImageUrl(url: String) {
        _currImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name:String,amountString: String,priceString:String){

    }

    fun searchForImage(imageQuery:String){

    }

}