package com.example.e_commerce.ui.login.signin

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.e_commerce.R
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.utils.LoginUtil
import dagger.hilt.android.lifecycle.HiltViewModel
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



    private val _mutableStateFlow = MutableStateFlow<DefaultStates>(DefaultStates.Idle)
    val states : StateFlow<DefaultStates> get() = _mutableStateFlow

    private val handler = CoroutineExceptionHandler { _, throwable -> _mutableStateFlow.value = DefaultStates.Error(throwable.message!!) ; _mutableStateFlow.value = DefaultStates.Idle}


    fun signInFirebase() {
        val result = LoginUtil.checkSignInValid(email.toString(),password.toString())
        if (result == R.string.success) {
            viewModelScope.launch(handler) {
                       _mutableStateFlow.value = DefaultStates.Loading
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