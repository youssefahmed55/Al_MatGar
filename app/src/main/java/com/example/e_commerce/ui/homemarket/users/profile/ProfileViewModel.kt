package com.example.e_commerce.ui.homemarket.users.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.e_commerce.Constants.USER_MODEL
import com.example.e_commerce.pojo.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel(){

    private val _mutableLiveDataUserModel = MutableLiveData<UserModel?>()
    val liveDataUserModel :LiveData<UserModel?> =_mutableLiveDataUserModel


    init {
        val userModel = savedStateHandle.get<UserModel>(USER_MODEL)
        _mutableLiveDataUserModel.value = userModel

    }
}