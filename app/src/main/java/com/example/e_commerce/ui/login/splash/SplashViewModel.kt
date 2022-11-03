package com.example.e_commerce.ui.login.splash


import android.app.Application
import androidx.lifecycle.*
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.ui.login.LoginStates
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await


class SplashViewModel(val app: Application) : AndroidViewModel(app) {

    private val _mutableStateFlow = MutableStateFlow<LoginStates>(LoginStates.Idle)
    val states : MutableStateFlow<LoginStates> get() = _mutableStateFlow

    private val handler = CoroutineExceptionHandler() { _, throwable -> _mutableStateFlow.value = LoginStates.Error(throwable.message!!) ; _mutableStateFlow.value = LoginStates.Idle  }


    fun signInAnonymous() {
       viewModelScope.launch(handler) {
           _mutableStateFlow.value = LoginStates.Loading
           delay(2000)
           withContext(Dispatchers.IO){
               FirebaseAuth.getInstance().signInAnonymously().await()
               Firebase.firestore.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("Info").document(FirebaseAuth.getInstance().currentUser!!.uid).set(UserModel(FirebaseAuth.getInstance().currentUser!!.uid, "", "", "", "Anonymous", "", "", "", "")).await()
           }
           _mutableStateFlow.value = LoginStates.Success(getApplication<Application>().applicationContext.getString(R.string.Sign_In_Anonymous_Successfully))
           _mutableStateFlow.value = LoginStates.Idle
       }

    }




    }
