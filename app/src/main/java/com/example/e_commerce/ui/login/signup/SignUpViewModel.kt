package com.example.e_commerce.ui.login.signup


import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.ui.login.LoginStates
import com.example.e_commerce.utils.RegisterUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

private const val TAG = "SignUpViewModel"
@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpRepo: SignUpRepo, @ApplicationContext private val appContext: Context) : ViewModel() {


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

    private val handler = CoroutineExceptionHandler() { _, throwable -> _mutableStateFlow.value = LoginStates.Error(throwable.message!!)}


    fun signUpFirebase() {

        val result =  RegisterUtil.checkSignUpValid(appContext,fullName.toString(),email.toString(),phone.toString(),password.toString(),confirmPassword.toString())
        if (result == appContext.getString(R.string.success)) {
            viewModelScope.launch(handler) {
                if(_mutableStateFlow.value != LoginStates.Loading) {
                    _mutableStateFlow.value = LoginStates.Loading
                    delay(2000)
                    signUpRepo.createUserFireBase(email.toString().trim(),password.toString().trim(),phone.toString().trim(),fullName.toString().trim())
                    _mutableStateFlow.value = LoginStates.Success(appContext.getString(R.string.Please_Check_Your_Email_to_Verification_Make_Sure_To_Check_Spam_Messages))
                }

            }
        }else{
            _errorMessage.value = result
        }
    }



   /* private suspend fun getDocuments(): List<DocumentSnapshot> {
        val querySnapshot = Firebase.firestore.collection("Users")
            .get()
            .await()
        return querySnapshot.documents
    }*/




}