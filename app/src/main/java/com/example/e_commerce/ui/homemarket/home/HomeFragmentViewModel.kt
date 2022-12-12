package com.example.e_commerce.ui.homemarket.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.Constants.BEAUTY
import com.example.e_commerce.Constants.CLOTHES
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

    private val _mutableStateFlowClothesModels = MutableStateFlow(emptyList<Product>())
    val stateFlowClothesModels : StateFlow<List<Product>> get() = _mutableStateFlowClothesModels

    private val _mutableStateFlowHouseWareModels = MutableStateFlow(emptyList<Product>())
    val stateFlowHouseWareModels : StateFlow<List<Product>> get() = _mutableStateFlowHouseWareModels

    private val _mutableStateFlowSliderList = MutableStateFlow(emptyList<SliderModel>())
    val stateFlowSliderList : StateFlow<List<SliderModel>> get() = _mutableStateFlowSliderList

    private val _mutableStateFlowCategoryModels = MutableStateFlow(emptyList<Category>())
    val stateFlowCategoryModels : StateFlow<List<Category>> get() = _mutableStateFlowCategoryModels


    val mutableStateFlowHideCategories = MutableStateFlow(false)
    val mutableStateFlowHideBeauty = MutableStateFlow(false)
    val mutableStateFlowHideFood = MutableStateFlow(false)
    val mutableStateFlowHideClothes = MutableStateFlow(false)
    val mutableStateFlowHideHouseWare = MutableStateFlow(false)
    val mutableStateFlowIsRefreshing = MutableStateFlow(false)

    val _error = MutableLiveData<String>()
    val error : LiveData<String> get() = _error

    private val handler = CoroutineExceptionHandler { _, throwable -> _error.postValue(throwable.message!!) }
    private val job = SupervisorJob() + handler
    init {

        refreshData()
    }

    fun onRefresh(){
        mutableStateFlowIsRefreshing.value = true
        refreshData()
        mutableStateFlowIsRefreshing.value = false
    }

    fun refreshData(){
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
            homeFragmentRepo.getProductsByCategoryType(CLOTHES)
            _mutableStateFlowClothesModels.value = homeFragmentRepo.getProductsDataBaseByCategoryType(CLOTHES)
            mutableStateFlowHideClothes.value = true
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
               _mutableStateFlowClothesModels.value = homeFragmentRepo.getProductsDataBaseByCategoryType(CLOTHES)
               mutableStateFlowHideClothes.value = true
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
}