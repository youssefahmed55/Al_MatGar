package com.example.e_commerce.ui.login.signin

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.e_commerce.R
import com.example.e_commerce.ui.login.LoginStates
import com.example.e_commerce.utils.LoginUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val TAG = "SignInViewModel"
@HiltViewModel
class SignInViewModel @Inject constructor(private val signInRepo: SignInRepo) : ViewModel() {



    val _email = ObservableField("")
    var email: String?
        get() = _email.get()
        set(value) = _email.set(value)


    val _password = ObservableField("")
    var password: String?
        get() = _password.get()
        set(value) = _password.set(value)


    private val _errorMessage = MutableLiveData<Int?>()
    val liveDataErrorMessage : LiveData<Int?> get() = _errorMessage



    private val _mutableStateFlow = MutableStateFlow<LoginStates>(LoginStates.Idle)
    val states : StateFlow<LoginStates> get() = _mutableStateFlow

    private val handler = CoroutineExceptionHandler() { _, throwable -> _mutableStateFlow.value = LoginStates.Error(throwable.message!!) ; _mutableStateFlow.value = LoginStates.Idle}


    fun signInFirebase() {
        val result = LoginUtil.checkSignInValid(email.toString(),password.toString())
        if (result == R.string.success) {
            viewModelScope.launch(handler) {
                       _mutableStateFlow.value = LoginStates.Loading
                       delay(2000)
                       _mutableStateFlow.value = signInRepo.signInWithEmailAndPassword(email.toString().trim(), password.toString().trim())
            }
        }else{
            _errorMessage.value = result
            _errorMessage.value = null
        }
    }


    fun firebaseAuthWithGoogle(idToken : String){
        viewModelScope.launch(handler) {
            _mutableStateFlow.value = signInRepo.signInWithGoogle(idToken)
        }


    }

}