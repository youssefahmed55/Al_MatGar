package com.example.e_commerce.ui.homemarket.orders.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.pojo.Order
import com.example.e_commerce.pojo.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrdersCustomerViewModel @Inject constructor(private val ordersCustomerRepo: OrdersCustomerRepo) : ViewModel() {

    private val _mutableStateFlowOrderModels = MutableStateFlow(emptyList<Order>())
    val stateFlowOrderModels : StateFlow<List<Order>> get() = _mutableStateFlowOrderModels

    val mutableLiveDataProduct = MutableLiveData<Product?>()
    val liveDataProduct : LiveData<Product?> get() = mutableLiveDataProduct

    private val _mutableStateFlowIsLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> get() = _mutableStateFlowIsLoading

    val errorMessage = MutableLiveData<String>()
    val error : LiveData<String> get() = errorMessage
    //Initialize handler to handle Coroutine Exception
    private val handler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message!!)   ;  _mutableStateFlowIsLoading.value = false}

    init {
        getOrders()
    }
    //Get Orders Of Customer
    private fun getOrders(){
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowOrderModels.value = ordersCustomerRepo.getOrders()
            _mutableStateFlowIsLoading.value = false

        }
    }

     //get Product From FireStore
     fun getProduct(productId : String){
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            mutableLiveDataProduct.value = ordersCustomerRepo.getProduct(productId)
            _mutableStateFlowIsLoading.value = false
        }
    }
}