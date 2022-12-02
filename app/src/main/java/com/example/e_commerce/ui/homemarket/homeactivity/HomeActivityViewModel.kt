package com.example.e_commerce.ui.homemarket.homeactivity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeActivityViewModel"
@HiltViewModel
class HomeActivityViewModel @Inject constructor(private val homeActivityRepo: HomeActivityRepo) : ViewModel(){

    private val _mutableStateFlowProfileImage = MutableStateFlow("")
    val stateFlowProfileImage : StateFlow<String> get() = _mutableStateFlowProfileImage

    private val _mutableStateFlowWelcomeMessage = MutableStateFlow("")
    val stateFlowWelcomeMessage : StateFlow<String> get() = _mutableStateFlowWelcomeMessage


    private val _mutableStateFlowMenu = MutableStateFlow(R.menu.bottom_nav_menu)
    val stateFlowMenu : StateFlow<Int> get() = _mutableStateFlowMenu


    private val handler = CoroutineExceptionHandler { _, throwable -> Log.d(TAG, "handler: ${throwable.message!!}") }
    init {
        viewModelScope.launch(handler) {
            _mutableStateFlowProfileImage.value = homeActivityRepo.getImageUrl()
            _mutableStateFlowWelcomeMessage.value = homeActivityRepo.getWelcomeMessage()
            _mutableStateFlowMenu.value = homeActivityRepo.getMenuByGetType()
        }
    }
}