package com.example.e_commerce.ui.login.signup

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.ui.login.LoginStates
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

private const val TAG = "SignUpViewModel"
class SignUpViewModel(val app: Application) : AndroidViewModel(app) {


    val _fullName = ObservableField("")
    var fullName: String?
        get() = _fullName.get()
        set(value) = _fullName.set(value)

    val _email = ObservableField("")
    var email: String?
        get() = _email.get()
        set(value) = _email.set(value)

    val _phone = ObservableField("")
    var phone: String?
        get() = _phone.get()
        set(value) = _phone.set(value)

    val _password = ObservableField("")
    var password: String?
        get() = _password.get()
        set(value) = _password.set(value)

    val _confirmPassword = ObservableField("")
    var confirmPassword: String?
        get() = _confirmPassword.get()
        set(value) = _confirmPassword.set(value)

    private val _errorMessage = MutableLiveData(0)
    val liveDataErrorMessage : LiveData<Int> get() = _errorMessage

    private val _mutableStateFlow = MutableStateFlow<LoginStates>(LoginStates.Idle)
    val states : MutableStateFlow<LoginStates> get() = _mutableStateFlow

    val handler = CoroutineExceptionHandler() { _, throwable -> _mutableStateFlow.value = LoginStates.Error(throwable.message!!)}

    private val  _mutableLiveDataGoogleSignInClient  = MutableLiveData<GoogleSignInClient>()
    val googleSignInClient : MutableLiveData<GoogleSignInClient> get() = _mutableLiveDataGoogleSignInClient

    fun signUpFirebase() {
        if (checkIfAllDataValid()) {
            viewModelScope.launch(handler) {
                if(_mutableStateFlow.value != LoginStates.Loading) {
                    _mutableStateFlow.value = LoginStates.Loading
                    delay(2000)
                    withContext(Dispatchers.IO) {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.toString().trim(), password.toString().trim()).await()
                        FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().await()
                        Firebase.firestore.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).set(UserModel(fullName.toString().trim(),email.toString().trim(),phone.toString().trim(),password.toString().trim(),"Customer","","","")).await()
                    }
                    _mutableStateFlow.value = LoginStates.Success("Please Check Your Email to Verification")
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
        Log.d("Hiiii", "checkIfAllDataValid: ")
        if(fullName.toString().trim().isEmpty()){_errorMessage.value =1 ; return false}
        if(email.toString().trim().isEmpty()){_errorMessage.value= 2 ; return false}
        if(phone.toString().trim().isEmpty()){_errorMessage.value= 3 ; return false}
        if(password.toString().trim().isEmpty()){_errorMessage.value = 4 ; return false}
        if(confirmPassword.toString().trim().isEmpty()){_errorMessage.value = 5 ; return false}
        if(password.toString().trim() != confirmPassword.toString().trim()){_errorMessage.value = 6 ; return false}

        return true
    }


}