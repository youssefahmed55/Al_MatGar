package com.example.e_commerce.ui.homemarket.users.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
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

    private val _mutableStateFlowType = MutableStateFlow(R.id.all_usersFragment)
    val stateFlowType : StateFlow<Int> get() = _mutableStateFlowType

    private val _mutableStateFlowState = MutableStateFlow<DefaultStates>(DefaultStates.Idle)
    val states : StateFlow<DefaultStates> get() = _mutableStateFlowState

    private val handler = CoroutineExceptionHandler { _, throwable -> _mutableStateFlowState.value  = DefaultStates.Error(throwable.message!!) ; _mutableStateFlowState.value  = DefaultStates.Idle}

    init {
        viewModelScope.launch(handler) {
            _mutableStateFlowProfileImage.value = usersRepo.getImageUrl()
        }
    }

     fun refreshData(){
        viewModelScope.launch(handler) {
            _mutableStateFlowState.value = DefaultStates.Loading
            _mutableStateFlowUserModels.value = usersRepo.getAllUserModels()
            _mutableStateFlowState.value = DefaultStates.Idle
        }
    }

    fun getAllUsers(){
        _mutableStateFlowType.value = R.id.all_usersFragment
        refreshData()

    }
    fun getAdminUsers(){
        _mutableStateFlowType.value = R.id.admins_usersFragment
        refreshData()

    }

    fun getCustomerUsers(){
        _mutableStateFlowType.value = R.id.customers_usersFragment
        refreshData()
    }

    fun getMerchantUsers(){
        _mutableStateFlowType.value = R.id.merchants_usersFragment
        refreshData()
    }


}