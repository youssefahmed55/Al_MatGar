package com.example.e_commerce.utils

import android.content.Context
import com.example.e_commerce.R

object LoginUtil {


    fun checkSignInValid(context : Context, email: String, password: String): String {
        if(email.trim().isEmpty()){return context.getString(R.string.Email_Is_Required)}
        if(password.trim().isEmpty()){return context.getString(R.string.Password_Is_Required)}

        return context.getString(R.string.success)
    }

}