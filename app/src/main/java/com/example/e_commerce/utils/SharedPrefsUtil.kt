package com.example.e_commerce.utils

import android.content.Context
import com.example.e_commerce.Constants
import com.example.e_commerce.pojo.UserModel
import com.google.gson.Gson


object SharedPrefsUtil{

    fun saveUserModel(context: Context ,userModel: UserModel){
        val gson = Gson()
        val json = gson.toJson(userModel)
        context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putString("userModel", json).apply()
    }

    fun getUserModel(context: Context):UserModel?{
        val gson = Gson()
        val json = context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE).getString("userModel", "")

        return if (json != "") {
            gson.fromJson(json, UserModel::class.java)
        } else null
    }

    fun clearUserModel(context: Context){
        context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putString("userModel", "").apply()
    }

    fun getImageUrl(context: Context) : String?{
        val user = getUserModel(context)
        return  user?.image

    }

    fun getName(context: Context) : String?{
        val user = getUserModel(context)
        return  user?.fullName

    }

    fun getId(context: Context) : String?{
        val user = getUserModel(context)
        return  user?.id

    }

}
