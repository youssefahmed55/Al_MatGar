package com.example.e_commerce.ui.login.splash


import androidx.lifecycle.*
import com.example.e_commerce.DefaultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val splashRepo: SplashRepo) : ViewModel()  {

    private val _mutableStateFlow = MutableStateFlow<DefaultStates>(DefaultStates.Idle)
    val states : StateFlow<DefaultStates> get() = _mutableStateFlow

    private val handler = CoroutineExceptionHandler { _, throwable -> _mutableStateFlow.value = DefaultStates.Error(throwable.message!!) ; _mutableStateFlow.value = DefaultStates.Idle  }


    fun signInAnonymous() {
       viewModelScope.launch(handler) {
           _mutableStateFlow.value = DefaultStates.Loading
           delay(2000)
           _mutableStateFlow.value = splashRepo.signInAnonymously()
           _mutableStateFlow.value = DefaultStates.Idle
       }

    }




    }
