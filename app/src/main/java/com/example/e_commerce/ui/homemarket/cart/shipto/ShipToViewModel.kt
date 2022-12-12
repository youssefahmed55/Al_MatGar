package com.example.e_commerce.ui.homemarket.cart.shipto

import androidx.lifecycle.*
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.pojo.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShipToViewModel @Inject constructor(private val shipToRepo: ShipToRepo, private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _mutableLiveDataUserModel = MutableLiveData<UserModel>()
    val liveDataUserModel : LiveData<UserModel> get() = _mutableLiveDataUserModel

    private val _mutableLiveDataItemsCount = MutableLiveData<Int>()
    val liveDataItemsCount : LiveData<Int> get() = _mutableLiveDataItemsCount

    private val _mutableLiveDataTotalPrice = MutableLiveData<Double>()
    val liveDataTotalPrice : LiveData<Double> get() = _mutableLiveDataTotalPrice

    private val _mutableStateFlow = MutableStateFlow<DefaultStates>(DefaultStates.Idle)
    val states : StateFlow<DefaultStates> get() = _mutableStateFlow

    private val handler = CoroutineExceptionHandler { _, throwable -> _mutableStateFlow.value = DefaultStates.Error(throwable.message!!)}

    private val listOfProducts = savedStateHandle.get<List<Product>>("listOfProducts") as List<Product>
    private val listOfCounts = savedStateHandle.get<List<Int>>("listOfCounts") as List<Int>
    init {
      getUserModel()
      getCountsAndTotalPrice()
    }

    private fun getUserModel(){
        viewModelScope.launch {
            _mutableLiveDataUserModel.value = shipToRepo.getUserModelData()
        }
    }

    private fun getCountsAndTotalPrice(){
        var itemsCount = 0
        var totalPrice = 0.0

        listOfCounts.forEach { itemsCount += it }

        for (i in listOfProducts.indices){
            totalPrice += if (listOfProducts[i].hasOffer)
                listOfProducts[i].offerPrice * listOfCounts[i]
            else
                listOfProducts[i].price * listOfCounts[i]
        }

        _mutableLiveDataItemsCount.value = itemsCount
        _mutableLiveDataTotalPrice.value = totalPrice
    }

    fun createOrders(){
        viewModelScope.launch(handler) {
            _mutableStateFlow.value = DefaultStates.Loading
            _mutableStateFlow.value = shipToRepo.createOrder(listOfProducts,listOfCounts)
        }

    }
}