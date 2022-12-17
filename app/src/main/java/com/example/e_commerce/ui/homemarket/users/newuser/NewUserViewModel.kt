package com.example.e_commerce.ui.homemarket.users.newuser

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.e_commerce.R
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.utils.NewUserUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NewUSerViewModel @Inject constructor(private val newUserRepo: NewUserRepo) : ViewModel() {


    val _fullName = ObservableField("")
    var fullName: String?
        get() = _fullName.get()
        set(value) = _fullName.set(value)

    val _email = ObservableField("")
    var email: String?
        get() = _email.get()
        set(value) = _email.set(value)

     val _gender = ObservableField(0)
    var gender: Int?
        get() = _gender.get()
        set(value) = _gender.set(value)

    val _type= ObservableField(0)
    var type: Int?
        get() = _type.get()
        set(value) = _type.set(value)

    val _dateOfBirthday= ObservableField("")
    var dateOfBirthday: String?
        get() = _dateOfBirthday.get()
        set(value) = _dateOfBirthday.set(value)

    val _phone = ObservableField("")
    var phone: String?
        get() = _phone.get()
        set(value) = _phone.set(value)

    val _address = ObservableField("")
    var address: String?
        get() = _address.get()
        set(value) = _address.set(value)

    val _password = ObservableField("")
    var password: String?
        get() = _password.get()
        set(value) = _password.set(value)



    private val _errorMessage = MutableLiveData<Int>()
    val liveDataErrorMessage : LiveData<Int> get() = _errorMessage

    private val _mutableStateFlow = MutableStateFlow<DefaultStates>(DefaultStates.Idle)
    val states : StateFlow<DefaultStates> get() = _mutableStateFlow

    //Initialize handler to handle Coroutine Exception
    private val handler = CoroutineExceptionHandler { _, throwable -> _mutableStateFlow.value = DefaultStates.Error(throwable.message!!)}


    fun createAccount(){
        // Get result Check Create Account Data Entered
        val result = NewUserUtil.checkCreateAccountValid(fullName.toString(),email.toString(),gender.toString().toInt(),type.toString().toInt(),dateOfBirthday.toString(),phone.toString(),address.toString(),password.toString())
        if(result == R.string.success){
            viewModelScope.launch(handler) {
                _mutableStateFlow.value = DefaultStates.Loading
                _mutableStateFlow.value = newUserRepo.createAccount(fullName.toString(),email.toString(),gender.toString().toInt(),type.toString().toInt(),dateOfBirthday.toString(),phone.toString(),address.toString(),password.toString())
            }
        }else{
            _errorMessage.value = result
        }


    }

}