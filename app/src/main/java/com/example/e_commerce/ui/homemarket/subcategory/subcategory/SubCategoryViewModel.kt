package com.example.e_commerce.ui.homemarket.subcategory.subcategory


import androidx.lifecycle.*
import com.example.e_commerce.Constants.CATEGORY_ID
import com.example.e_commerce.Constants.CATEGORY_NAME
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.ui.homemarket.sharedrepo.FavoritesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SubCategoryViewModel @Inject constructor(private val subCategoryRepo: SubCategoryRepo, private val favoritesRepo: FavoritesRepo, savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _mutableStateFlowProductModels = MutableStateFlow(emptyList<Product>())
    val stateFlowProductModels : StateFlow<List<Product>> get() = _mutableStateFlowProductModels

    val mutableStateFlowTextSearch = MutableStateFlow("")

    private val _mutableStateFlowIsLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> get() = _mutableStateFlowIsLoading

    val errorMessage = MutableLiveData<String>()
    val error : LiveData<String> get() = errorMessage

    private val _deletedFromFavorites = MutableLiveData<String?>()
    val deleteFromFavorites : LiveData<String?> get() = _deletedFromFavorites

    private val _addedToFavorites = MutableLiveData<String?>()
    val addedToFavorites : LiveData<String?> get() = _addedToFavorites

    private val _mutableLiveDataNameOfCategory = MutableLiveData<String>()
    val nameOfCategory : LiveData<String> = _mutableLiveDataNameOfCategory
    //Initialize handler to handle Coroutine Exception
    private val handler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message!!)   ;  _mutableStateFlowIsLoading.value = false}

    init {
        val catId = savedStateHandle.get<Int>(CATEGORY_ID)        //Get Category Id
        val catName = savedStateHandle.get<String>(CATEGORY_NAME) //Get Category Name
        getAllProductsByTypeId(catId!!)                           //Get All Products By Type Id
        _mutableLiveDataNameOfCategory.value = catName!!
    }


    private fun getAllProductsByTypeId(id : Int){
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowProductModels.value = subCategoryRepo.getAllProducts(id)
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