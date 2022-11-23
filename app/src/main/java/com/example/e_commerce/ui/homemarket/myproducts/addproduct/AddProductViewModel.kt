package com.example.e_commerce.ui.homemarket.myproducts.addproduct

import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.utils.NewProductUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val addProductRepo: AddProductRepo) : ViewModel()   {

    val _nameOfProduct = ObservableField("")
    var nameOfProduct: String?
        get() = _nameOfProduct.get()
        set(value) = _nameOfProduct.set(value)

    val _description = ObservableField("")
    var description: String?
        get() = _description.get()
        set(value) = _description.set(value)

    val _categorie = ObservableField(0)
    var categorie: Int?
        get() = _categorie.get()
        set(value) = _categorie.set(value)

    val _deliveryTime = ObservableField(0)
    var deliveryTime: Int?
        get() = _deliveryTime.get()
        set(value) = _deliveryTime.set(value)

    val _price = ObservableField("")
    var price: String?
        get() = _price.get()
        set(value) = _price.set(value)

    val _hasOffer = ObservableField(false)
    var hasOffer: Boolean?
        get() = _hasOffer.get()
        set(value) = _hasOffer.set(value)

    val _offerPrice = ObservableField("")
    var offerPrice: String?
        get() = _offerPrice.get()
        set(value) = _offerPrice.set(value)

    private val _errorMessage = MutableLiveData<Int>()
    val liveDataErrorMessage : LiveData<Int> get() = _errorMessage

    private val _mutableStateFlow = MutableStateFlow<DefaultStates>(DefaultStates.Idle)
    val states : StateFlow<DefaultStates> get() = _mutableStateFlow

    private val handler = CoroutineExceptionHandler { _, throwable -> _mutableStateFlow.value  = DefaultStates.Error(throwable.message!!) ;}

    fun saveNewProduct(listOfImages : List<Uri>){
         val result = NewProductUtil.checkCreateProductValid(nameOfProduct.toString(),description.toString(),price.toString(),hasOffer!!,offerPrice.toString(),listOfImages)
         if (result == R.string.success){
              viewModelScope.launch(handler) {
                  _mutableStateFlow.value = DefaultStates.Loading
                  _mutableStateFlow.value = addProductRepo.createNewProduct(nameOfProduct.toString(),description.toString(),price.toString(),hasOffer!!,offerPrice.toString(),categorie!!,deliveryTime!!,listOfImages)

              }
         }else{
             _errorMessage.value = result
         }

    }
}