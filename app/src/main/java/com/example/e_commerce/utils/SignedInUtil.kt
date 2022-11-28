package com.example.e_commerce.utils

import android.content.Context
import com.example.e_commerce.Constants

object SignedInUtil {
    fun setIsSignIn(context : Context,r : Boolean){
        context.getSharedPreferences(Constants.PREFERENCE_NAME_SIGNED, Context.MODE_PRIVATE).edit().putBoolean("isSignedIn", r).apply()
    }

    fun getIsSignIn( context : Context): Boolean {
        return context.getSharedPreferences(Constants.PREFERENCE_NAME_SIGNED, Context.MODE_PRIVATE).getBoolean("isSignedIn",false)
    }
}