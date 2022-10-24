package com.example.e_commerce.ui.login.signin

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.e_commerce.ui.login.LoginStates
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

private const val TAG = "SignInViewModel"
class SignInViewModel(val app: Application) : AndroidViewModel(app) {



    val _email = ObservableField("")
    var email: String?
        get() = _email.get()
        set(value) = _email.set(value)


    val _password = ObservableField("")
    var password: String?
        get() = _password.get()
        set(value) = _password.set(value)


    private val _errorMessage = MutableLiveData(0)
    val liveDataErrorMessage : LiveData<Int> get() = _errorMessage



    private val _mutableStateFlow = MutableStateFlow<LoginStates>(LoginStates.Idle)
    val states : MutableStateFlow<LoginStates> get() = _mutableStateFlow

    val handler = CoroutineExceptionHandler() { _, throwable -> _mutableStateFlow.value = LoginStates.Error(throwable.message!!)}

    private val  _mutableLiveDataGoogleSignInClient  = MutableLiveData<GoogleSignInClient>()
    val googleSignInClient : MutableLiveData<GoogleSignInClient> get() = _mutableLiveDataGoogleSignInClient

    fun signInFirebase() {
        if (checkIfAllDataValid()) {
            viewModelScope.launch(handler) {
                   if (_mutableStateFlow.value != LoginStates.Loading) {
                       _mutableStateFlow.value = LoginStates.Loading
                       delay(2000)
                       withContext(Dispatchers.IO) {
                           FirebaseAuth.getInstance().signInWithEmailAndPassword(email.toString().trim(), password.toString().trim()).await()
                       }
                       if (FirebaseAuth.getInstance().currentUser!!.isEmailVerified) {
                           _mutableStateFlow.value = LoginStates.Success("Sign In Successfully")
                       } else {
                           _mutableStateFlow.value = LoginStates.Error("please verify your email address,it may take few minutes")
                       }
                   }
            }
        }
    }

    fun signInGoogle(){

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("703335808379-bgiibd78b4oh15ips6an9dgbl259flhk.apps.googleusercontent.com")
            .requestEmail()
            .build()

        _mutableLiveDataGoogleSignInClient.value = GoogleSignIn.getClient(getApplication<Application>().applicationContext, gso)

    }

    fun firebaseAuthWithGoogle(idToken : String){
        viewModelScope.launch {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            withContext(Dispatchers.IO){
                FirebaseAuth.getInstance().signInWithCredential(credential).await()
            }
            _mutableStateFlow.value = LoginStates.Success("Sign In With Google Account Successfully")

        }


    }




    private fun checkIfAllDataValid(): Boolean {
        Log.d(TAG, "checkIfAllDataValid: ")
        if(email.toString().trim().isEmpty()){_errorMessage.value= 2 ;_errorMessage.value= 0 ; return false}
        if(password.toString().trim().isEmpty()){_errorMessage.value = 4 ;_errorMessage.value= 0; return false}

        return true
    }


}