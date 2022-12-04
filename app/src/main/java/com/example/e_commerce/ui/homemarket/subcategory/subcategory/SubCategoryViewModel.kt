package com.example.e_commerce.ui.homemarket.subcategory.subcategory


import androidx.lifecycle.*
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.ui.homemarket.sharedrepo.FavoritesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SubCategoryViewModel @Inject constructor(private val subCategoryRepo: SubCategoryRepo, private val favoritesRepo: FavoritesRepo, private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _mutableStateFlowProductModels = MutableStateFlow(emptyList<Product>())
    val stateFlowProductModels : StateFlow<List<Product>> get() = _mutableStateFlowProductModels

    private val _mutableStateFlowFavorites = MutableStateFlow(emptyList<String>())
    val stateFlowFavorites : StateFlow<List<String>> get() = _mutableStateFlowFavorites

    val mutableStateFlowTextSearch = MutableStateFlow("")

    private val _mutableStateFlowIsLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> get() = _mutableStateFlowIsLoading

    val _error = MutableLiveData<String>()
    val error : LiveData<String> get() = _error

    private val _mutableLiveDataNameOfCategory = MutableLiveData<String>()
    val nameOfCategory : LiveData<String> = _mutableLiveDataNameOfCategory

    private val handler = CoroutineExceptionHandler { _, throwable -> _error.postValue(throwable.message!!)   ;  _mutableStateFlowIsLoading.value = false}

    init {
        val catId = savedStateHandle.get<Int>("catId")
        val catName = savedStateHandle.get<String>("catName")
        getAllAndFavorite(catId!!)
        _mutableLiveDataNameOfCategory.value = catName!!
    }


    fun getAllAndFavorite(id : Int){
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowFavorites.value = favoritesRepo.getListOfFavoritesId()
            _mutableStateFlowProductModels.value = subCategoryRepo.getAllProducts(id)
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