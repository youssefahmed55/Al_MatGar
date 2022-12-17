package com.example.e_commerce.ui.homemarket.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.Constants.BEAUTY
import com.example.e_commerce.Constants.ELECTRONICS
import com.example.e_commerce.Constants.FOOD
import com.example.e_commerce.Constants.HOUSE_WARE
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

    val mutableStateFlowTextSearch = MutableStateFlow("")

    private val _mutableStateFlowType = MutableStateFlow(R.id.beauty_exploreFragment)
    val stateFlowType : StateFlow<Int> get() = _mutableStateFlowType

    private val _mutableStateFlowIsLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> get() = _mutableStateFlowIsLoading

    val errorMessage = MutableLiveData<String>()
    val error : LiveData<String> get() = errorMessage

    private val _deletedFromFavorites = MutableLiveData<String?>()
    val deleteFromFavorites : LiveData<String?> get() = _deletedFromFavorites

    private val _addedToFavorites = MutableLiveData<String?>()
    val addedToFavorites : LiveData<String?> get() = _addedToFavorites
    //Initialize handler to handle Coroutine Exception
    private val handler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message!!)   ;  _mutableStateFlowIsLoading.value = false}

    init {
        getBeautyProduct()
    }
    //Get List Of Beauty Products
    fun getBeautyProduct() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.beauty_exploreFragment
            _mutableStateFlowProductModels.value = exploreRepo.getListProductsByType(BEAUTY)
            _mutableStateFlowIsLoading.value = false
        }
    }
    //Get List Of Electronics Products
    fun getElectronicsSProduct() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.Electronics_exploreFragment
            _mutableStateFlowProductModels.value = exploreRepo.getListProductsByType(ELECTRONICS)
            _mutableStateFlowIsLoading.value = false
        }
    }
    //Get List Of Food Products
    fun getFoodProduct() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.food_exploreFragment
            _mutableStateFlowProductModels.value = exploreRepo.getListProductsByType(FOOD)
            _mutableStateFlowIsLoading.value = false
        }
    }
    //Get List Of HouseWare Products
    fun getHouseWareProduct() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.houseWare_exploreFragment
            _mutableStateFlowProductModels.value = exploreRepo.getListProductsByType(HOUSE_WARE)
            _mutableStateFlowIsLoading.value = false
        }
    }
    //Edit Item (Add Or Remove Item From Favorites)
    fun editFavorite(id : String, isFavorite : Boolean){
        viewModelScope.launch(handler) {
            if (isFavorite) {
                favoritesRepo.deleteFromFavorite(id)
                _deletedFromFavorites.value = id
                _deletedFromFavorites.value = null
            }
            else {
                favoritesRepo.addToFavorite(id)
                _addedToFavorites.value = id
                _addedToFavorites.value = null
            }
        }
    }

}