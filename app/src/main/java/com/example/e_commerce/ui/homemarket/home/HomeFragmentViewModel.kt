package com.example.e_commerce.ui.homemarket.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.adapters.SliderAdapter
import com.example.e_commerce.pojo.Category
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.pojo.SliderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val homeFragmentRepo: HomeFragmentRepo) : ViewModel() {


    val _error = MutableLiveData<String>()
    val error : LiveData<String> get() = _error


    private val _mutableStateFlowBeautyModels = MutableStateFlow(emptyList<Product>())
    val stateFlowBeautyModels : StateFlow<List<Product>> get() = _mutableStateFlowBeautyModels

    private val _mutableStateFlowFoodModels = MutableStateFlow(emptyList<Product>())
    val stateFlowFoodModels : StateFlow<List<Product>> get() = _mutableStateFlowFoodModels

    private val _mutableStateFlowClothesModels = MutableStateFlow(emptyList<Product>())
    val stateFlowClothesModels : StateFlow<List<Product>> get() = _mutableStateFlowClothesModels

    private val _mutableStateFlowHouseWareModels = MutableStateFlow(emptyList<Product>())
    val stateFlowHouseWareModels : StateFlow<List<Product>> get() = _mutableStateFlowHouseWareModels

    private val _mutableStateFlowSliderAdapter = MutableStateFlow(SliderAdapter(emptyList()))
    val stateFlowSliderAdapter : StateFlow<SliderAdapter> get() = _mutableStateFlowSliderAdapter

    private val _mutableStateFlowCategoryModels = MutableStateFlow(emptyList<Category>())
    val stateFlowCategoryModels : StateFlow<List<Category>> get() = _mutableStateFlowCategoryModels

    private val _mutableStateFlowProfileImage = MutableStateFlow("")
    val stateFlowProfileImage : StateFlow<String> get() = _mutableStateFlowProfileImage

    private val _mutableStateFlowWelcomeMessage = MutableStateFlow("")
    val stateFlowWelcomeMessage : StateFlow<String> get() = _mutableStateFlowWelcomeMessage

    val mutableStateFlowHideCategories = MutableStateFlow(false)
    val mutableStateFlowHideBeauty = MutableStateFlow(false)
    val mutableStateFlowHideFood = MutableStateFlow(false)
    val mutableStateFlowHideClothes = MutableStateFlow(false)
    val mutableStateFlowHideHouseWare = MutableStateFlow(false)
    val mutableStateFlowIsRefreshing = MutableStateFlow(false)

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
                _mutableStateFlowProfileImage.value = homeFragmentRepo.getImageUrl()
                _mutableStateFlowWelcomeMessage.value = homeFragmentRepo.getWelcomeMessage()
        }

        launch(job) {
            homeFragmentRepo.getSliderProductsImages()
            val sliders = homeFragmentRepo.getSlidersDataBase()
            val mutableListOfImages = mutableListOf<String>()
            sliders.forEach { mutableListOfImages.add(it.image!!) }
            _mutableStateFlowSliderAdapter.value = SliderAdapter(mutableListOfImages)
        }

        launch(job) {
            homeFragmentRepo.getCategories()
            _mutableStateFlowCategoryModels.value = homeFragmentRepo.getCategoriesDataBase()
            mutableStateFlowHideCategories.value = true
        }

        launch(job) {
            homeFragmentRepo.getBeautyProducts()
            _mutableStateFlowBeautyModels.value = homeFragmentRepo.getBeautyProductsDataBase()
            mutableStateFlowHideBeauty.value = true
        }
        launch(job) {
            homeFragmentRepo.getClothesProducts()
            _mutableStateFlowClothesModels.value = homeFragmentRepo.getClothesProductsDataBase()
            mutableStateFlowHideClothes.value = true
        }
        launch(job) {
            homeFragmentRepo.getFoodProducts()
            _mutableStateFlowFoodModels.value = homeFragmentRepo.getFoodProductsDataBase()
            mutableStateFlowHideFood.value = true

        }
        launch(job) {
            homeFragmentRepo.getHouseWareProducts()
            _mutableStateFlowHouseWareModels.value = homeFragmentRepo.getHouseWareProductsDataBase()
            mutableStateFlowHideHouseWare.value = true
        }
        }

    }
    fun getRoomDataBase(){
       viewModelScope.launch {

           launch(job) {
               val sliders = homeFragmentRepo.getSlidersDataBase()
               val mutableListOfImages = mutableListOf<String>()
               sliders.forEach { mutableListOfImages.add(it.image!!) }
               _mutableStateFlowSliderAdapter.value = SliderAdapter(mutableListOfImages)
           }

           launch(job) {
               _mutableStateFlowCategoryModels.value = homeFragmentRepo.getCategoriesDataBase()
               mutableStateFlowHideCategories.value = true
           }

           launch(job) {
               _mutableStateFlowBeautyModels.value = homeFragmentRepo.getBeautyProductsDataBase()
               mutableStateFlowHideBeauty.value = true
           }
           launch(job) {
               _mutableStateFlowClothesModels.value = homeFragmentRepo.getClothesProductsDataBase()
               mutableStateFlowHideClothes.value = true
           }
           launch(job) {
               _mutableStateFlowFoodModels.value = homeFragmentRepo.getFoodProductsDataBase()
               mutableStateFlowHideFood.value = true

           }
           launch(job) {
               _mutableStateFlowHouseWareModels.value = homeFragmentRepo.getHouseWareProductsDataBase()
               mutableStateFlowHideHouseWare.value = true
           }
       }

    }
}