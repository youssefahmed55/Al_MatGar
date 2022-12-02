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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UsersViewModel @Inject constructor(private val usersRepo: UsersRepo) : ViewModel() {

    private val _mutableStateFlowUserModels = MutableStateFlow(emptyList<UserModel>())
    val stateFlowUserModels : StateFlow<List<UserModel>> get() = _mutableStateFlowUserModels

    val mutableStateFlowTextSearch = MutableStateFlow("")

    private val _mutableStateFlowType = MutableStateFlow(R.id.all_usersFragment)
    val stateFlowType : StateFlow<Int> get() = _mutableStateFlowType

    val _error = MutableLiveData<String>()
    val error : LiveData<String> get() = _error

    private val _mutableStateFlowIsLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> get() = _mutableStateFlowIsLoading

    private val handler = CoroutineExceptionHandler { _, throwable -> _error.postValue(throwable.message!!) ; _mutableStateFlowIsLoading.value = false}


     fun refreshData(){
        viewModelScope.launch(handler) {
            _mutableStateFlowIsLoading.value = true
            _mutableStateFlowUserModels.value = usersRepo.getAllUserModels()
            _mutableStateFlowIsLoading.value = false
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