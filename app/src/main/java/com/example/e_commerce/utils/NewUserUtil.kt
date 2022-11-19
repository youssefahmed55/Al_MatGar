package com.example.e_commerce.utils

import com.example.e_commerce.R

object NewUserUtil {

    fun checkCreateAccountValid(fullName : String, email: String, gender : Int, type : Int , birthday :String, phone: String, location :String, password: String): Int {

        if(fullName.trim().isEmpty()){return R.string.FullName_Is_Required}
        if(email.trim().isEmpty()){return R.string.Email_Is_Required}
        if(gender == 0){return R.string.Gender_Is_Required}
        if(type == 0){return R.string.Type_Is_Required}
        if(birthday.trim().isEmpty()){return R.string.Birthday_Is_Required}
        if(phone.trim().isEmpty()){return R.string.Phone_Number_Is_Required}
        if(location.trim().isEmpty()){return R.string.Location_Is_Required}
        if(password.trim().isEmpty()){return R.string.Password_Is_Required}

        return R.string.success
    }
}