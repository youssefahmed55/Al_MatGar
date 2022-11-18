package com.example.e_commerce.ui.homemarket.users.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UsersViewModel @Inject constructor(private val usersRepo: UsersRepo) : ViewModel() {

    private val _mutableStateFlowUserModels = MutableStateFlow(emptyList<UserModel>())
    val stateFlowUserModels : StateFlow<List<UserModel>> get() = _mutableStateFlowUserModels

    private val _mutableStateFlowProfileImage = MutableStateFlow("")
    val stateFlowProfileImage : StateFlow<String> get() = _mutableStateFlowProfileImage

    val _mutableStateFlowTextSearch = MutableStateFlow("")
    val stateFlowTextSearch : StateFlow<String> get() = _mutableStateFlowTextSearch

    private val _mutableStateFlowType = MutableStateFlow(0)
    val stateFlowType : StateFlow<Int> get() = _mutableStateFlowType

    private val _errorChannel = Channel<String>(Channel.UNLIMITED)
    val errorMessage : Channel<String> get() = _errorChannel

    private val handler = CoroutineExceptionHandler { _, throwable -> viewModelScope.launch { _errorChannel.send(throwable.message!!) ; _mutableStateFlowUserModels.value = emptyList()} }

    init {
        viewModelScope.launch(handler) {
            _mutableStateFlowProfileImage.value = usersRepo.getImageUrl()
        }
        getAllUsers()
    }

     fun getAllUsers(){
        viewModelScope.launch(handler) {
            _mutableStateFlowType.value = R.id.all_usersFragment
            _mutableStateFlowUserModels.value = usersRepo.getAllUserModels()
        }
    }

    fun getAdminUsers(){
        viewModelScope.launch(handler) {
            _mutableStateFlowType.value = R.id.admins_usersFragment
            _mutableStateFlowUserModels.value = usersRepo.getAdminsUserModels()

        }
    }

    fun getCustomerUsers(){
        viewModelScope.launch(handler) {
            _mutableStateFlowType.value = R.id.customers_usersFragment
            _mutableStateFlowUserModels.value = usersRepo.getCustomersUserModels()

        }
    }

    fun getMerchantUsers(){
        viewModelScope.launch(handler) {
            _mutableStateFlowType.value = R.id.merchants_usersFragment
            _mutableStateFlowUserModels.value = usersRepo.getMerchantsUserModels()

        }
    }


}