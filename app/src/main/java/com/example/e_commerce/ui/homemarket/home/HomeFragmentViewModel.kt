package com.example.e_commerce.ui.homemarket.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.adapters.SliderAdapter
import com.example.e_commerce.pojo.Category
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.pojo.SliderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val homeFragmentRepo: HomeFragmentRepo) : ViewModel() {


    private val mutableStateFlowError = MutableStateFlow("")
    val errorState : StateFlow<String> get() = mutableStateFlowError


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

    private val handler = CoroutineExceptionHandler { _, throwable -> mutableStateFlowError.value = throwable.message!! }

    init {
        refreshData()
    }

    fun onRefresh(){
        mutableStateFlowIsRefreshing.value = true
        refreshData()
        mutableStateFlowIsRefreshing.value = false
    }

    fun refreshData(){
        viewModelScope.launch(handler) {
            _mutableStateFlowProfileImage.value = homeFragmentRepo.getImageUrl()
            _mutableStateFlowWelcomeMessage.value = homeFragmentRepo.getWelcomeMessage()
        }
        viewModelScope.launch(handler) {
            _mutableStateFlowSliderAdapter.value = SliderAdapter(homeFragmentRepo.getSliderProductsImages())
        }

        viewModelScope.launch(handler) {
            _mutableStateFlowCategoryModels.value = homeFragmentRepo.getCategories()
            mutableStateFlowHideCategories.value = true
        }
        viewModelScope.launch(handler) {
            _mutableStateFlowBeautyModels.value = homeFragmentRepo.getBeautyProducts()
            mutableStateFlowHideBeauty.value = true
        }

        viewModelScope.launch(handler) {
            _mutableStateFlowFoodModels.value = homeFragmentRepo.getFoodProducts()
            mutableStateFlowHideFood.value = true
        }

        viewModelScope.launch(handler) {
            _mutableStateFlowClothesModels.value = homeFragmentRepo.getClothesProducts()
            mutableStateFlowHideClothes.value = true
        }

        viewModelScope.launch(handler) {
            _mutableStateFlowHouseWareModels.value = homeFragmentRepo.getHouseWareProducts()
            mutableStateFlowHideHouseWare.value = true
        }
    }
}