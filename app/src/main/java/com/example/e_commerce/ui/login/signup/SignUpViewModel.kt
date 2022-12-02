package com.example.e_commerce.ui.login.signup


import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.e_commerce.R
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.utils.RegisterUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val TAG = "SignUpViewModel"
@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpRepo: SignUpRepo) : ViewModel() {


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

    private val _errorMessage = MutableLiveData<Int>()
    val liveDataErrorMessage : LiveData<Int> get() = _errorMessage

    private val _mutableStateFlow = MutableStateFlow<DefaultStates>(DefaultStates.Idle)
    val states : StateFlow<DefaultStates> get() = _mutableStateFlow

    private val handler = CoroutineExceptionHandler { _, throwable -> _mutableStateFlow.value = DefaultStates.Error(throwable.message!!)}


    fun signUpFirebase() {

        val result =  RegisterUtil.checkSignUpValid(fullName.toString(),email.toString(),phone.toString(),password.toString(),confirmPassword.toString())
        if (result == R.string.success) {
            viewModelScope.launch(handler) {
                    _mutableStateFlow.value = DefaultStates.Loading
                    delay(2000)
                    _mutableStateFlow.value = signUpRepo.createUserFireBase(email.toString().trim(),password.toString().trim(),phone.toString().trim(),fullName.toString().trim())
            }
        }else{
            _errorMessage.value = result
        }
    }


}