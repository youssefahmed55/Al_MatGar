package com.example.e_commerce.ui.login.splash


import android.app.Application
import androidx.lifecycle.*
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.DefaultStates
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await


class SplashViewModel(val app: Application) : AndroidViewModel(app) {

    private val _mutableStateFlow = MutableStateFlow<DefaultStates>(DefaultStates.Idle)
    val states : StateFlow<DefaultStates> get() = _mutableStateFlow

    private val handler = CoroutineExceptionHandler { _, throwable -> _mutableStateFlow.value = DefaultStates.Error(throwable.message!!) ; _mutableStateFlow.value = DefaultStates.Idle  }


    fun signInAnonymous() {
       viewModelScope.launch(handler) {
           _mutableStateFlow.value = DefaultStates.Loading
           delay(2000)
           withContext(Dispatchers.IO){
               FirebaseAuth.getInstance().signInAnonymously().await()
               Firebase.firestore.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("Info").document(FirebaseAuth.getInstance().currentUser!!.uid).set(UserModel(FirebaseAuth.getInstance().currentUser!!.uid, "", "", "Anonymous", false, "", "", "", "")).await()
           }
           _mutableStateFlow.value = DefaultStates.Success(getApplication<Application>().applicationContext.getString(R.string.Sign_In_Anonymous_Successfully))
           _mutableStateFlow.value = DefaultStates.Idle
       }

    }




    }
