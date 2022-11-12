package com.example.e_commerce

sealed class DefaultStates{
    object Idle : DefaultStates()
    object Loading : DefaultStates()
    data class Success(val toastMessage : String) : DefaultStates()
    data class Error(val error : String) : DefaultStates()

}
