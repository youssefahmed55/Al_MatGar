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
import com.example.e_commerce.utils.RegisterUtil
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

    private val _errorMessage = MutableLiveData<String>()
    val liveDataErrorMessage : LiveData<String> get() = _errorMessage

    private val _mutableStateFlow = MutableStateFlow<LoginStates>(LoginStates.Idle)
    val states : MutableStateFlow<LoginStates> get() = _mutableStateFlow

    val handler = CoroutineExceptionHandler() { _, throwable -> _mutableStateFlow.value = LoginStates.Error(throwable.message!!)}

    private val  _mutableLiveDataGoogleSignInClient  = MutableLiveData<GoogleSignInClient>()
    val googleSignInClient : MutableLiveData<GoogleSignInClient> get() = _mutableLiveDataGoogleSignInClient

    fun signUpFirebase() {

        val result =  RegisterUtil.checkSignUpValid(getApplication<Application>().applicationContext,fullName.toString(),email.toString(),phone.toString(),password.toString(),confirmPassword.toString())
        if (result == "Success") {
            viewModelScope.launch(handler) {
                if(_mutableStateFlow.value != LoginStates.Loading) {
                    _mutableStateFlow.value = LoginStates.Loading
                    delay(2000)
                    withContext(Dispatchers.IO) {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.toString().trim(), password.toString().trim()).await()
                        Log.d(TAG, "signUpFirebase: " + FirebaseAuth.getInstance().currentUser!!.uid)
                        FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().await()
                        Firebase.firestore.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).set(UserModel(fullName.toString().trim(),email.toString().trim(),phone.toString().trim(),password.toString().trim(),"Customer","","","","")).await()
                    }
                    _mutableStateFlow.value = LoginStates.Success("Please Check Your Email to Verification, Make Sure To Check Spam Messages")
                }

            }
        }else{
            _errorMessage.value = result
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




}