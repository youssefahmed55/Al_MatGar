package com.example.e_commerce.ui.homemarket.homeactivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeActivityViewModel @Inject constructor(private val homeActivityRepo: HomeActivityRepo) : ViewModel(){

    private val _mutableStateFlowProfileImage = MutableStateFlow("")
    val stateFlowProfileImage : StateFlow<String> get() = _mutableStateFlowProfileImage

    private val _mutableStateFlowWelcomeMessage = MutableStateFlow("")
    val stateFlowWelcomeMessage : StateFlow<String> get() = _mutableStateFlowWelcomeMessage

    private val _mutableLiveDataMenu = MutableLiveData<Int>()
    val liveDataMenu : LiveData<Int> get() = _mutableLiveDataMenu

    private val _mutableLiveDataSignedOut = MutableLiveData<Boolean>()
    val liveDataSignedOut : LiveData<Boolean> get() = _mutableLiveDataSignedOut

    private val errorMessage = MutableLiveData<String>()
    val error : LiveData<String> get() = errorMessage
    //Initialize handler to handle Coroutine Exception
    private val handler = CoroutineExceptionHandler { _, throwable -> errorMessage.postValue(throwable.message) }
    init {
        viewModelScope.launch(handler) {
            getProfileImage()
            _mutableStateFlowWelcomeMessage.value = homeActivityRepo.getWelcomeMessage()
            _mutableLiveDataMenu.value = homeActivityRepo.getMenuByGetType()
            homeActivityRepo.updateMyToken()
        }
    }

    fun getProfileImage(){
        viewModelScope.launch(handler) {
            _mutableStateFlowProfileImage.value = homeActivityRepo.getImageUrl()
        }
    }

    fun signOut(){
        viewModelScope.launch(handler) {
            homeActivityRepo.removeMyToken()
            homeActivityRepo.signOut()
            _mutableLiveDataSignedOut.value = true
        }
    }
}