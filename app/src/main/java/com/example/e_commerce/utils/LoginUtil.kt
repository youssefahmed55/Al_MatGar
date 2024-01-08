package com.example.e_commerce.utils

import com.example.e_commerce.R

object LoginUtil {


    fun checkSignInValid(email: String, password: String): Int {
        if (email.trim().isEmpty() && password.trim().isEmpty()){return R.string.no_data_exists}
        if(email.trim().isEmpty()){return R.string.Email_Is_Required}
        if(password.trim().isEmpty()){return R.string.Password_Is_Required}

        return R.string.success
    }

}