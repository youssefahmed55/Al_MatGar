package com.example.e_commerce.ui.login

sealed class LoginStates{
    object Idle : LoginStates()
    object Loading : LoginStates()
    data class Success(val toastMessage : String) : LoginStates()
    data class Error(val error : String) : LoginStates()

}
