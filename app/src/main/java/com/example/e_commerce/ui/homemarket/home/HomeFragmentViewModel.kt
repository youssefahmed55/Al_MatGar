package com.example.e_commerce.ui.homemarket.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.Constants.BEAUTY
import com.example.e_commerce.Constants.ELECTRONICS
import com.example.e_commerce.Constants.FOOD
import com.example.e_commerce.Constants.HOUSE_WARE
import com.example.e_commerce.pojo.Category
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.pojo.SliderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val homeFragmentRepo: HomeFragmentRepo) : ViewModel() {

    private val _mutableStateFlowBeautyModels = MutableStateFlow(emptyList<Product>())
    val stateFlowBeautyModels : StateFlow<List<Product>> get() = _mutableStateFlowBeautyModels

    private val _mutableStateFlowFoodModels = MutableStateFlow(emptyList<Product>())
    val stateFlowFoodModels : StateFlow<List<Product>> get() = _mutableStateFlowFoodModels

    private val _mutableStateFlowElectronicsModels = MutableStateFlow(emptyList<Product>())
    val stateFlowElectronicsModels : StateFlow<List<Product>> get() = _mutableStateFlowElectronicsModels

    private val _mutableStateFlowHouseWareModels = MutableStateFlow(emptyList<Product>())
    val stateFlowHouseWareModels : StateFlow<List<Product>> get() = _mutableStateFlowHouseWareModels

    private val _mutableStateFlowSliderList = MutableStateFlow(emptyList<SliderModel>())
    val stateFlowSliderList : StateFlow<List<SliderModel>> get() = _mutableStateFlowSliderList

    private val _mutableStateFlowCategoryModels = MutableStateFlow(emptyList<Category>())
    val stateFlowCategoryModels : StateFlow<List<Category>> get() = _mutableStateFlowCategoryModels

    val mutableLiveDataProduct = MutableLiveData<Product?>()
    val liveDataProduct : LiveData<Product?> get() = mutableLiveDataProduct


    val mutableStateFlowHideCategories = MutableStateFlow(false)
    val mutableStateFlowHideBeauty = MutableStateFlow(false)
    val mutableStateFlowHideFood = MutableStateFlow(false)
    val mutableStateFlowHideElectronics = MutableStateFlow(false)
    val mutableStateFlowHideHouseWare = MutableStateFlow(false)
    val mutableStateFlowIsRefreshing = MutableStateFlow(false)

    val errorMessage = MutableLiveData<String>()
    val error : LiveData<String> get() = errorMessage
    //Initialize handler to handle Coroutine Exception
    private val handler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message!!) }
    private val job = SupervisorJob() + handler   //Initialize Supervisor Job
    init {
        refreshData() //Refresh Data
    }

    fun onRefresh(){
        mutableStateFlowIsRefreshing.value = true
        refreshData() //Refresh Data
        mutableStateFlowIsRefreshing.value = false
    }
   //Refresh Data
   private fun refreshData(){
        viewModelScope.launch{

        launch(job) {
            homeFragmentRepo.getSliderProductsImages()
            _mutableStateFlowSliderList.value = homeFragmentRepo.getSlidersDataBase()
        }

        launch(job) {
            homeFragmentRepo.getCategories()
            _mutableStateFlowCategoryModels.value = homeFragmentRepo.getCategoriesDataBase()
            mutableStateFlowHideCategories.value = true
        }

        launch(job) {
            homeFragmentRepo.getProductsByCategoryType(BEAUTY)
            _mutableStateFlowBeautyModels.value = homeFragmentRepo.getProductsDataBaseByCategoryType(BEAUTY)
            mutableStateFlowHideBeauty.value = true
        }
        launch(job) {
            homeFragmentRepo.getProductsByCategoryType(ELECTRONICS)
            _mutableStateFlowElectronicsModels.value = homeFragmentRepo.getProductsDataBaseByCategoryType(ELECTRONICS)
            mutableStateFlowHideElectronics.value = true
        }
        launch(job) {
            homeFragmentRepo.getProductsByCategoryType(FOOD)
            _mutableStateFlowFoodModels.value = homeFragmentRepo.getProductsDataBaseByCategoryType(FOOD)
            mutableStateFlowHideFood.value = true

        }
        launch(job) {
            homeFragmentRepo.getProductsByCategoryType(HOUSE_WARE)
            _mutableStateFlowHouseWareModels.value = homeFragmentRepo.getProductsDataBaseByCategoryType(HOUSE_WARE)
            mutableStateFlowHideHouseWare.value = true
        }
        }

    }
    //Get Room DataBase
    fun getRoomDataBase(){
       viewModelScope.launch {

           launch(job) {
               _mutableStateFlowSliderList.value = homeFragmentRepo.getSlidersDataBase()
           }

           launch(job) {
               _mutableStateFlowCategoryModels.value = homeFragmentRepo.getCategoriesDataBase()
               mutableStateFlowHideCategories.value = true
           }

           launch(job) {
               _mutableStateFlowBeautyModels.value = homeFragmentRepo.getProductsDataBaseByCategoryType(BEAUTY)
               mutableStateFlowHideBeauty.value = true
           }
           launch(job) {
               _mutableStateFlowElectronicsModels.value = homeFragmentRepo.getProductsDataBaseByCategoryType(ELECTRONICS)
               mutableStateFlowHideElectronics.value = true
           }
           launch(job) {
               _mutableStateFlowFoodModels.value = homeFragmentRepo.getProductsDataBaseByCategoryType(FOOD)
               mutableStateFlowHideFood.value = true

           }
           launch(job) {
               _mutableStateFlowHouseWareModels.value = homeFragmentRepo.getProductsDataBaseByCategoryType(HOUSE_WARE)
               mutableStateFlowHideHouseWare.value = true
           }
       }

    }
    //Get Product Of Slider By Id
    fun getProductOfSliderById(productId : String){
        viewModelScope.launch(handler) {
            mutableLiveDataProduct.value = homeFragmentRepo.getProduct(productId)
        }

    }
}