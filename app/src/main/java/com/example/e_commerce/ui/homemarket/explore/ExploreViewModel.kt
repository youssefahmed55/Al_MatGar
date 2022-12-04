package com.example.e_commerce.ui.homemarket.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.ui.homemarket.sharedrepo.FavoritesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(private val exploreRepo: ExploreRepo, private val favoritesRepo: FavoritesRepo) : ViewModel() {

    private val _mutableStateFlowProductModels = MutableStateFlow(emptyList<Product>())
    val stateFlowProductModels : StateFlow<List<Product>> get() = _mutableStateFlowProductModels

    private val _mutableStateFlowFavorites = MutableStateFlow(emptyList<String>())
    val stateFlowFavorites : StateFlow<List<String>> get() = _mutableStateFlowFavorites

    val mutableStateFlowTextSearch = MutableStateFlow("")

    private val _mutableStateFlowType = MutableStateFlow(R.id.beauty_exploreFragment)
    val stateFlowType : StateFlow<Int> get() = _mutableStateFlowType

    private val _mutableStateFlowIsLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> get() = _mutableStateFlowIsLoading

    val _error = MutableLiveData<String>()
    val error : LiveData<String> get() = _error

    private val handler = CoroutineExceptionHandler { _, throwable -> _error.postValue(throwable.message!!)   ;  _mutableStateFlowIsLoading.value = false}

    init {
        getBeautyProduct()
    }

    fun getFavorites(){
        viewModelScope.launch(handler) {
            _mutableStateFlowFavorites.value = favoritesRepo.getListOfFavoritesId()
        }
    }

    fun getBeautyProduct() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.beauty_exploreFragment
            _mutableStateFlowProductModels.value = exploreRepo.getBeauty()
            _mutableStateFlowIsLoading.value = false
        }
    }

    fun getClothesProduct() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.clothes_exploreFragment
            _mutableStateFlowProductModels.value = exploreRepo.getClothes()
            _mutableStateFlowIsLoading.value = false
        }
    }

    fun getFoodProduct() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.food_exploreFragment
            _mutableStateFlowProductModels.value = exploreRepo.getFood()
            _mutableStateFlowIsLoading.value = false
        }
    }

    fun getHouseWareProduct() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.houseWare_exploreFragment
            _mutableStateFlowProductModels.value = exploreRepo.getHouseWare()
            _mutableStateFlowIsLoading.value = false
        }
    }

    fun addToFavorite(id : String){
        viewModelScope.launch(handler) {
            favoritesRepo.addToFavorite(id)
        }
    }

    fun deleteFromFavorite(id : String){
        viewModelScope.launch(handler) {
            favoritesRepo.deleteFromFavorite(id)
        }
    }

}