package com.example.e_commerce.ui.homemarket.favorite

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
class FavoritesFragmentViewModel @Inject constructor(private val favoritesFragmentRepo: FavoritesFragmentRepo, private val favoritesRepo: FavoritesRepo) : ViewModel() {

    private val _mutableStateFlowProductModels = MutableStateFlow(emptyList<Product>())
    val stateFlowProductModels : StateFlow<List<Product>> get() = _mutableStateFlowProductModels

    val mutableStateFlowTextSearch = MutableStateFlow("")

    private val _mutableStateFlowType = MutableStateFlow(R.id.beauty_favoritesFragment)
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
        getBeautyProductFavorites()
    }
    //Get Beauty Favorite Products
    fun getBeautyProductFavorites() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.beauty_favoritesFragment
            _mutableStateFlowProductModels.value = favoritesFragmentRepo.getListOfFavoritesByCategory(BEAUTY)
            _mutableStateFlowIsLoading.value = false
        }
    }
    //Get Electronics Favorite Products
    fun getElectronicsProductFavorites() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.electronics_favoritesFragment
            _mutableStateFlowProductModels.value = favoritesFragmentRepo.getListOfFavoritesByCategory(ELECTRONICS)
            _mutableStateFlowIsLoading.value = false
        }
    }
    //Get Food Favorite Products
    fun getFoodProductFavorites() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.food_favoritesFragment
            _mutableStateFlowProductModels.value = favoritesFragmentRepo.getListOfFavoritesByCategory(FOOD)
            _mutableStateFlowIsLoading.value = false
        }
    }
    //Get HouseWare Favorite Products
    fun getHouseWareProductFavorites() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.houseWare_favoritesFragment
            _mutableStateFlowProductModels.value = favoritesFragmentRepo.getListOfFavoritesByCategory(HOUSE_WARE)
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