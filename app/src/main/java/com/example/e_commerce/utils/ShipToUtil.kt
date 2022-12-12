package com.example.e_commerce.utils

import com.example.e_commerce.R

object ShipToUtil {

    fun checkIfDataRequiredAvailable(location : String, phone : String) : Int{
        if (location.isEmpty()){
            return R.string.Location_Is_Required
        }

        if (phone.isEmpty()){
            return R.string.Phone_Number_Is_Required
        }

        return R.string.success
    }
}