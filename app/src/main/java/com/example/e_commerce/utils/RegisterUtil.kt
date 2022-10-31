package com.example.e_commerce.utils

import android.content.Context
import com.example.e_commerce.R

object RegisterUtil {

    fun checkSignUpValid(context : Context, fullName : String, email: String, phone: String, password: String, confirmPassword: String): String {
        if(fullName.trim().isEmpty()){return context.getString(R.string.FullName_Is_Required)}
        if(email.trim().isEmpty()){return context.getString(R.string.Email_Is_Required)}
        if(phone.trim().isEmpty()){return context.getString(R.string.Phone_Number_Is_Required)}
        if(password.trim().isEmpty()){return context.getString(R.string.Password_Is_Required)}
        if(confirmPassword.trim().isEmpty()){return context.getString(R.string.Confirm_Password_Is_Required)}
        if(password.trim() != confirmPassword.trim()){return context.getString(R.string.Password_Donot_match)}

        return context.getString(R.string.success)
    }

}