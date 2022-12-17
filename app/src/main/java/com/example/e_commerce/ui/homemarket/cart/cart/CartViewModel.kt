package com.example.e_commerce.ui.homemarket.cart.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.pojo.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepo: CartRepo) : ViewModel() {

    private val _mutableStateFlowProductModels = MutableStateFlow(emptyList<Product>())
    val stateFlowProductModels : StateFlow<List<Product>> get() = _mutableStateFlowProductModels

    private val _mutableStateFlowProductCounts = MutableStateFlow(emptyList<Int>())
    val stateFlowProductCounts : StateFlow<List<Int>> get() = _mutableStateFlowProductCounts

    val mutableStateFlowTextSearch = MutableStateFlow("")

    private val _mutableStateFlowIsLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> get() = _mutableStateFlowIsLoading

    val errorMessage = MutableLiveData<String>()
    val error : LiveData<String> get() = errorMessage
    //Initialize handler to handle Coroutine Exception
    private val handler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message!!)   ;  _mutableStateFlowIsLoading.value = false}

    init {
        getListAndCountsOfInCart()
    }
   //get List Of Products And Counts From FireStore
   private fun getListAndCountsOfInCart(){
       viewModelScope.launch(handler) {
           _mutableStateFlowIsLoading.value = true
           val list = cartRepo.getInCartProducts()
           _mutableStateFlowProductModels.value = list
           _mutableStateFlowProductCounts.value = cartRepo.getProductsCount(list)
           _mutableStateFlowIsLoading.value = false
       }
   }
    //delete Product And Count From FireStore By Id
    fun deleteProductFromInCard(id : String){
        viewModelScope.launch(handler) {
            cartRepo.deleteProductFromInCart(id)
            getListAndCountsOfInCart()
        }
    }

}