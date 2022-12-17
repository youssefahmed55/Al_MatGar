package com.example.e_commerce.ui.homemarket.myproducts.myproducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyProductsViewModel @Inject constructor(private val myProductsRepo: MyProductsRepo) : ViewModel() {

    private val _mutableStateFlowProductModels = MutableStateFlow(emptyList<Product>())
    val stateFlowProductModels : StateFlow<List<Product>> get() = _mutableStateFlowProductModels

    val mutableStateFlowTextSearch = MutableStateFlow("")

    private val _mutableStateFlowType = MutableStateFlow(R.id.all_myProductsFragment)
    val stateFlowType : StateFlow<Int> get() = _mutableStateFlowType

    val errorMessage = MutableLiveData<String>()
    val error : LiveData<String> get() = errorMessage

    private val _mutableStateFlowIsLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> get() = _mutableStateFlowIsLoading
    //Initialize handler to handle Coroutine Exception
    private val handler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message!!) ; _mutableStateFlowIsLoading.value = false}


    //Refresh Data
     fun refreshData(){
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowProductModels.value = myProductsRepo.getAllProductModels()
            _mutableStateFlowIsLoading.value = false
        }
    }
    //Get All Products Of Merchant
    fun getAllProducts(){
        _mutableStateFlowType.value = R.id.all_myProductsFragment
        refreshData() //Refresh Data

    }
    fun getBeauty(){
        _mutableStateFlowType.value = R.id.beauty_myProductsFragment
        refreshData() //Refresh Data

    }

    fun getElectronics(){
        _mutableStateFlowType.value = R.id.Electronics_myProductsFragment
        refreshData() //Refresh Data
    }

    fun getFood(){
        _mutableStateFlowType.value = R.id.food_myProductsFragment
        refreshData() //Refresh Data
    }

    fun getHouseWare(){
        _mutableStateFlowType.value = R.id.houseWare_myProductsFragment
        refreshData() //Refresh Data
    }
    //Delete Product From FireStore
    fun deleteProduct(productId : String , list: List<String>?){
        viewModelScope.launch(handler) {
            myProductsRepo.deleteProduct(productId,list)
            refreshData() //Refresh Data
        }
    }


}