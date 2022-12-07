package com.example.e_commerce.ui.homemarket.subcategory.productdetails


import androidx.lifecycle.*
import com.example.e_commerce.adapters.SliderAdapter
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.ui.homemarket.sharedrepo.FavoritesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val productDetailsRepo: ProductDetailsRepo, private val favoritesRepo: FavoritesRepo, private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _mutableLiveDataProduct = MutableLiveData<Product?>()
    val liveDataProduct : LiveData<Product?> get() = _mutableLiveDataProduct

    private val _mutableStateFlowSliderAdapter = MutableStateFlow(SliderAdapter(emptyList()))
    val stateFlowSliderAdapter : StateFlow<SliderAdapter> get() = _mutableStateFlowSliderAdapter

    val _error = MutableLiveData<String>()
    val error : LiveData<String> get() = _error

    val _count = MutableLiveData("1")
    val count : LiveData<String> get() = _count

    private val _mutableStateFlowType = MutableStateFlow<String>("")
    val stateType : StateFlow<String> get() = _mutableStateFlowType

    private val handler = CoroutineExceptionHandler { _, throwable -> _error.postValue(throwable.message!!)  }

    val product = savedStateHandle.get<Product>("product")

    init {
        _mutableLiveDataProduct.value =  product
        product?.images?.let { _mutableStateFlowSliderAdapter.value = SliderAdapter(it) }
        viewModelScope.launch(handler) {
            _mutableStateFlowType.value = productDetailsRepo.getUserType()
        }
    }

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

    fun addToCart(count : String){
        viewModelScope.launch(handler) {
            productDetailsRepo.addToCard(product!!,count.toInt())
        }
    }


}