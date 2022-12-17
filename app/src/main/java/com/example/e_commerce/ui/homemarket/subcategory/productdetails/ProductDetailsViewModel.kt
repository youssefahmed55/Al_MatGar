package com.example.e_commerce.ui.homemarket.subcategory.productdetails


import androidx.lifecycle.*
import com.example.e_commerce.Constants.PRODUCT
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.ui.homemarket.sharedrepo.FavoritesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val productDetailsRepo: ProductDetailsRepo, private val favoritesRepo: FavoritesRepo, savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _mutableLiveDataProduct = MutableLiveData<Product?>()
    val liveDataProduct : LiveData<Product?> get() = _mutableLiveDataProduct

    private val _mutableStateFlowSliderImagesList = MutableStateFlow(emptyList<String>())
    val stateFlowSliderImagesList : StateFlow<List<String>> get() = _mutableStateFlowSliderImagesList

    val errorMessage = MutableLiveData<String>()
    val error : LiveData<String> get() = errorMessage

    val countNumber = MutableLiveData("1")
    val count : LiveData<String> get() = countNumber

    private val _mutableStateFlowType = MutableStateFlow("")
    val stateType : StateFlow<String> get() = _mutableStateFlowType
    //Initialize handler to handle Coroutine Exception
    private val handler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message!!)  }

    val product = savedStateHandle.get<Product>(PRODUCT)

    init {
        _mutableLiveDataProduct.value =  product
        product?.images?.let { _mutableStateFlowSliderImagesList.value = product.images }
        viewModelScope.launch(handler) {
            _mutableStateFlowType.value = productDetailsRepo.getUserType()
        }
    }
    //Add Or Delete/Remove Item From Favorites
    fun addOrDeleteToFavorite(){
        viewModelScope.launch(handler) {
            if (product?.isFavorite!!){
                favoritesRepo.deleteFromFavorite(product.id)
                product.isFavorite = false
            }else {
                favoritesRepo.addToFavorite(product.id)
                product.isFavorite = true
            }
            _mutableLiveDataProduct.value = product
        }

    }
    //Add To Cart
    fun addToCart(count : String){
        viewModelScope.launch(handler) {
            productDetailsRepo.addToCard(product!!,count.toInt())
        }
    }


}