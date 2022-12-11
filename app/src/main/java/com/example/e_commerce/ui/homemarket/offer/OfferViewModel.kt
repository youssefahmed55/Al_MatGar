package com.example.e_commerce.ui.homemarket.offer

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
class OfferViewModel @Inject constructor(private val offerRepo: OfferRepo, private val favoritesRepo: FavoritesRepo) : ViewModel() {

    private val _mutableStateFlowProductModels = MutableStateFlow(emptyList<Product>())
    val stateFlowProductModels : StateFlow<List<Product>> get() = _mutableStateFlowProductModels

    val mutableStateFlowTextSearch = MutableStateFlow("")

    private val _mutableStateFlowType = MutableStateFlow(R.id.beauty_offerFragment)
    val stateFlowType : StateFlow<Int> get() = _mutableStateFlowType

    private val _mutableStateFlowIsLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> get() = _mutableStateFlowIsLoading

    val _error = MutableLiveData<String>()
    val error : LiveData<String> get() = _error

    private val _deletedFromFavorites = MutableLiveData<String?>()
    val deleteFromFavorites : LiveData<String?> get() = _deletedFromFavorites

    private val _addedToFavorites = MutableLiveData<String?>()
    val addedToFavorites : LiveData<String?> get() = _addedToFavorites

    private val handler = CoroutineExceptionHandler { _, throwable -> _error.postValue(throwable.message!!)   ;  _mutableStateFlowIsLoading.value = false}

    init {
        getBeautyProductOffers()
    }


    fun getBeautyProductOffers() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.beauty_offerFragment
            _mutableStateFlowProductModels.value = offerRepo.getBeautyOffers()
            _mutableStateFlowIsLoading.value = false
        }
    }

    fun getClothesProductOffers() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.clothes_offerFragment
            _mutableStateFlowProductModels.value = offerRepo.getClothesOffers()
            _mutableStateFlowIsLoading.value = false
        }
    }

    fun getFoodProductOffers() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.food_offerFragment
            _mutableStateFlowProductModels.value = offerRepo.getFoodOffers()
            _mutableStateFlowIsLoading.value = false
        }
    }

    fun getHouseWareProductOffers() {
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowType.value = R.id.houseWare_offerFragment
            _mutableStateFlowProductModels.value = offerRepo.getHouseWareOffers()
            _mutableStateFlowIsLoading.value = false
        }
    }

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