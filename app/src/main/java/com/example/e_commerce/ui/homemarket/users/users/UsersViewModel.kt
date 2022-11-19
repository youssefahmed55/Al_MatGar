package com.example.e_commerce.ui.homemarket.users.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.DefaultStates
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

    val mutableStateFlowTextSearch = MutableStateFlow("")

    private val _mutableStateFlowType = MutableStateFlow(0)
    val stateFlowType : StateFlow<Int> get() = _mutableStateFlowType

    private val _mutableStateFlowState = MutableStateFlow<DefaultStates>(DefaultStates.Idle)
    val states : StateFlow<DefaultStates> get() = _mutableStateFlowState

    private val handler = CoroutineExceptionHandler { _, throwable -> _mutableStateFlowState.value  = DefaultStates.Error(throwable.message!!) ; _mutableStateFlowState.value  = DefaultStates.Idle ; _mutableStateFlowUserModels.value = emptyList() }

    init {
        viewModelScope.launch(handler) {
            _mutableStateFlowProfileImage.value = usersRepo.getImageUrl()
        }
        getAllUsers()
    }

     fun getAllUsers(){
        viewModelScope.launch(handler) {
            _mutableStateFlowState.value = DefaultStates.Loading
            _mutableStateFlowType.value = R.id.all_usersFragment
            _mutableStateFlowUserModels.value = usersRepo.getAllUserModels()
            _mutableStateFlowState.value = DefaultStates.Idle
        }
    }

    fun getAdminUsers(){
        viewModelScope.launch(handler) {
            _mutableStateFlowState.value = DefaultStates.Loading
            _mutableStateFlowType.value = R.id.admins_usersFragment
            _mutableStateFlowUserModels.value = usersRepo.getAdminsUserModels()
            _mutableStateFlowState.value = DefaultStates.Idle
        }
    }

    fun getCustomerUsers(){
        viewModelScope.launch(handler) {
            _mutableStateFlowState.value = DefaultStates.Loading
            _mutableStateFlowType.value = R.id.customers_usersFragment
            _mutableStateFlowUserModels.value = usersRepo.getCustomersUserModels()
            _mutableStateFlowState.value = DefaultStates.Idle
        }
    }

    fun getMerchantUsers(){
        viewModelScope.launch(handler) {
            _mutableStateFlowState.value = DefaultStates.Loading
            _mutableStateFlowType.value = R.id.merchants_usersFragment
            _mutableStateFlowUserModels.value = usersRepo.getMerchantsUserModels()
            _mutableStateFlowState.value = DefaultStates.Idle
        }
    }


}