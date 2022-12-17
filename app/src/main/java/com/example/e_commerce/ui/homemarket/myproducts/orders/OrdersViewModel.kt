package com.example.e_commerce.ui.homemarket.myproducts.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.Constants.CANCELED
import com.example.e_commerce.Constants.COMPLETED
import com.example.e_commerce.Constants.PROCESS
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Order
import com.example.e_commerce.pojo.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrdersViewModel @Inject constructor(private val ordersRepo: OrdersRepo) : ViewModel() {

    private val _mutableStateFlowOrderModels = MutableStateFlow(emptyList<Order>())
    val stateFlowOrderModels : StateFlow<List<Order>> get() = _mutableStateFlowOrderModels

    val mutableStateFlowTextSearch = MutableStateFlow("")

    private val _mutableStateFlowType = MutableStateFlow(R.id.inProcess_ordersFragment)
    val stateFlowType : StateFlow<Int> get() = _mutableStateFlowType

    val mutableLiveDataProduct = MutableLiveData<Product?>()
    val liveDataProduct : LiveData<Product?> get() = mutableLiveDataProduct

    val errorMessage = MutableLiveData<String>()
    val error : LiveData<String> get() = errorMessage

    private val _mutableStateFlowIsLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> get() = _mutableStateFlowIsLoading
    //Initialize handler to handle Coroutine Exception
    private val handler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message!!) ; _mutableStateFlowIsLoading.value = false}

    init {
        getProcessProducts()
    }

    //Get Process Products
    fun getProcessProducts(){
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.inProcess_ordersFragment
            _mutableStateFlowOrderModels.value = ordersRepo.getOrdersByStatus(PROCESS)
            _mutableStateFlowIsLoading.value = false
        }
    }
    //Get Completed Products
    fun getCompletedProducts(){
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.completed_ordersFragment
            _mutableStateFlowOrderModels.value = ordersRepo.getOrdersByStatus(COMPLETED)
            _mutableStateFlowIsLoading.value = false
        }
    }
    //Get Canceled Products
    fun getCanceledProducts(){
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.canceled_ordersFragment
            _mutableStateFlowOrderModels.value = ordersRepo.getOrdersByStatus(CANCELED)
            _mutableStateFlowIsLoading.value = false
        }
    }
    //Get Product From FireStore By Id
    fun getProduct(productId : String){
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            mutableLiveDataProduct.value = ordersRepo.getProduct(productId)
            _mutableStateFlowIsLoading.value = false
        }
    }
    //Complete Order
    fun completeOrder(orderId : String, customerId : String){
        viewModelScope.launch(handler) {
            ordersRepo.setOrderCompleted(orderId, customerId)
            getProcessProducts()
        }
    }
    //Cancel Order
    fun cancelOrder(orderId : String, customerId : String){
        viewModelScope.launch(handler) {
            ordersRepo.setOrderCanceled(orderId, customerId)
            getProcessProducts()
        }
    }





}