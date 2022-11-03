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
import javax.inject.Inject

private const val TAG = "SignInViewModel"
@HiltViewModel
class SignInViewModel @Inject constructor(private val signInRepo: SignInRepo, @ApplicationContext private val appContext: Context ) : ViewModel() {



    val _email = ObservableField("")
    var email: String?
        get() = _email.get()
        set(value) = _email.set(value)


    val _password = ObservableField("")
    var password: String?
        get() = _password.get()
        set(value) = _password.set(value)


    private val _errorMessage = MutableLiveData<String>()
    val liveDataErrorMessage : LiveData<String> get() = _errorMessage



    private val _mutableStateFlow = MutableStateFlow<LoginStates>(LoginStates.Idle)
    val states : MutableStateFlow<LoginStates> get() = _mutableStateFlow

    private val handler = CoroutineExceptionHandler() { _, throwable -> _mutableStateFlow.value = LoginStates.Error(throwable.message!!) ; _mutableStateFlow.value = LoginStates.Idle}

    private val  _mutableLiveDataGoogleSignInClient  = MutableLiveData<GoogleSignInClient>()
    val googleSignInClient : MutableLiveData<GoogleSignInClient> get() = _mutableLiveDataGoogleSignInClient

    fun signInFirebase() {
        val result = LoginUtil.checkSignInValid(appContext,email.toString(),password.toString())
        if (result == appContext.getString(R.string.success)) {
            viewModelScope.launch(handler) {
                   if (_mutableStateFlow.value != LoginStates.Loading) {
                       _mutableStateFlow.value = LoginStates.Loading
                       delay(2000)
                       _mutableStateFlow.value = signInRepo.signInWithEmailAndPassword(email.toString().trim(), password.toString().trim())
                       //_mutableStateFlow.value = LoginStates.Idle
                   }
            }
        }else{
            _errorMessage.value = result
            _errorMessage.value = ""
        }
    }

    fun signInGoogle(){

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(appContext.getString(R.string.request_google))
            .requestEmail()
            .build()

        _mutableLiveDataGoogleSignInClient.value = GoogleSignIn.getClient(appContext, gso)

    }

    fun firebaseAuthWithGoogle(idToken : String){
        viewModelScope.launch(handler) {
             signInRepo.signInWithGoogle(idToken)
            _mutableStateFlow.value = LoginStates.Success(appContext.getString(R.string.Sign_In_With_Google_Account_Successfully))
        }


    }

}