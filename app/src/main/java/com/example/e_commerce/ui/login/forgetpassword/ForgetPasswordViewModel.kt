package com.example.e_commerce.ui.login.forgetpassword

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.e_commerce.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class ForgetPasswordViewModel(val app: Application) : AndroidViewModel(app)   {

    val _email = ObservableField("")
    var email: String?
        get() = _email.get()
        set(value) = _email.set(value)

    private val _errorMessage = MutableLiveData<String>()
    val liveDataErrorMessage : LiveData<String> get() = _errorMessage

    private val _errorToast = MutableLiveData<String>()
    val liveDataErrorToast : LiveData<String> get() = _errorToast

    private val _sentPassword = MutableLiveData<Boolean>()
    val liveDataSentPassword : LiveData<Boolean> get() = _sentPassword

    //Initialize handler to handle Coroutine Exception
    private val handler = CoroutineExceptionHandler { _, throwable -> _errorToast.value = throwable.message!!}


    fun receivePassword() {
        viewModelScope.launch(handler){
            if (email.toString().trim().isNotEmpty()){ //if Email EditText Not Empty
                withContext(Dispatchers.IO){
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email.toString().trim()).await() //Send Password Reset Email
                }
                _sentPassword.value = true
            }else{
                _errorMessage.value = getApplication<Application>().applicationContext.getString(R.string.Email_Is_Required)
            }

        }
    }

    }
