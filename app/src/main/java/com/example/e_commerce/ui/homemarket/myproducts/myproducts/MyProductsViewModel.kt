package com.example.e_commerce.ui.homemarket.myproducts.myproducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.pojo.UserModel
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

    private val _mutableStateFlowProfileImage = MutableStateFlow("")
    val stateFlowProfileImage : StateFlow<String> get() = _mutableStateFlowProfileImage

    val mutableStateFlowTextSearch = MutableStateFlow("")

    private val _mutableStateFlowType = MutableStateFlow(R.id.all_myProductsFragment)
    val stateFlowType : StateFlow<Int> get() = _mutableStateFlowType

    val _error = MutableLiveData<String>()
    val error : LiveData<String> get() = _error

    private val _mutableStateFlowIsLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> get() = _mutableStateFlowIsLoading

    private val handler = CoroutineExceptionHandler { _, throwable -> _error.postValue(throwable.message!!) ; _mutableStateFlowIsLoading.value = false}

    init {
        viewModelScope.launch(handler) {
            _mutableStateFlowProfileImage.value = myProductsRepo.getImageUrl()
        }
        refreshData()
    }

     fun refreshData(){
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowProductModels.value = myProductsRepo.getAllProductModels()
            _mutableStateFlowIsLoading.value = false
        }
    }

    fun getAllProducts(){
        _mutableStateFlowType.value = R.id.all_myProductsFragment
        refreshData()

    }
    fun getBeauty(){
        _mutableStateFlowType.value = R.id.beauty_myProductsFragment
        refreshData()

    }

    fun getClothes(){
        _mutableStateFlowType.value = R.id.clothes_myProductsFragment
        refreshData()
    }

    fun getFood(){
        _mutableStateFlowType.value = R.id.food_myProductsFragment
        refreshData()
    }

    fun getHouseWare(){
        _mutableStateFlowType.value = R.id.houseWare_myProductsFragment
        refreshData()
    }

    fun deleteProduct(productId : String , list: List<String>?){
        viewModelScope.launch(handler) {
            myProductsRepo.deleteProduct(productId,list)
            refreshData()
        }
    }


}