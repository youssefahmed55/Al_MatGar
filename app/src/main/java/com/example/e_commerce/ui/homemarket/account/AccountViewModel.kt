package com.example.e_commerce.ui.homemarket.account


import android.net.Uri
import androidx.lifecycle.*
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.DefaultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val accountRepo: AccountRepo) : ViewModel() {



    private val _mutableLiveDataUserModel = MutableLiveData<UserModel?>()
    val liveDataUserModel : LiveData<UserModel?> get() = _mutableLiveDataUserModel


    private val _mutableStateFlow = MutableStateFlow<DefaultStates>(DefaultStates.Idle)
    val states : StateFlow<DefaultStates> get() = _mutableStateFlow

    private val _mutableLiveDataSignedOut = MutableLiveData<Boolean>()
    val liveDataSignedOut : LiveData<Boolean> get() = _mutableLiveDataSignedOut
    //Initialize handler to handle Coroutine Exception
    private val handler = CoroutineExceptionHandler { _, throwable -> _mutableStateFlow.value = DefaultStates.Error(throwable.message!!)}


   init {
       viewModelScope.launch(handler) {
           _mutableLiveDataUserModel.value = accountRepo.userModel
       }
   }

    fun saveAllChanges(gender: String, birthday: String, email: String, passwordOfNewEmail: String?, phone: String, newPassword: String? , passwordOfNewPassword : String?, location: String, imageUri: Uri?) {
          viewModelScope.launch(handler) {
              _mutableStateFlow.value = DefaultStates.Loading
              if(gender.isNotEmpty()) accountRepo.changeGender(gender)
              if (birthday.isNotEmpty()) accountRepo.changeBirthday(birthday)
              if (phone.isNotEmpty()) accountRepo.changePhone(phone)
              if (location.isNotEmpty()) accountRepo.changeLocation(location)
              if (imageUri != null){accountRepo.changeProfileImage(imageUri)}
              if (newPassword != null && passwordOfNewPassword != null) accountRepo.changePassword(newPassword,passwordOfNewPassword)
              if (email.isNotEmpty() && passwordOfNewEmail != null) {
                  _mutableStateFlow.value = accountRepo.changeEmail(email,passwordOfNewEmail)
              }
              else
              _mutableStateFlow.value = DefaultStates.Success("Saved Successfully")
          }


    }

    fun signOut(){
        viewModelScope.launch(handler) {
            accountRepo.removeMyToken()
            accountRepo.signOut()
            _mutableLiveDataSignedOut.value = true
        }
    }






}