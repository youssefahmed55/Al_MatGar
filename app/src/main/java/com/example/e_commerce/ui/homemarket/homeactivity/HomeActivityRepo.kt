package com.example.e_commerce.ui.homemarket.homeactivity

import android.content.Context
import com.example.e_commerce.Constants.ADMIN
import com.example.e_commerce.Constants.MERCHANT
import com.example.e_commerce.R
import com.example.e_commerce.utils.SharedPrefsUtil
import com.example.e_commerce.utils.SignedInUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeActivityRepo @Inject constructor(@ApplicationContext private val appContext: Context) {

    suspend fun getImageUrl(): String = withContext(Dispatchers.IO) {
        return@withContext SharedPrefsUtil.getImageUrl(appContext)!!
    }

    suspend fun getWelcomeMessage(): String = withContext(Dispatchers.IO) {
        if (SignedInUtil.getIsSignIn(appContext)){
            return@withContext appContext.getString(R.string.welcome_back) + ' ' + SharedPrefsUtil.getName(appContext)
        }else{
            SignedInUtil.setIsSignIn(appContext,true)
            return@withContext appContext.getString(R.string.welcome) + ' ' + SharedPrefsUtil.getName(appContext)
        }

    }

    suspend fun getMenuByGetType(): Int = withContext(Dispatchers.IO) {
        when (SharedPrefsUtil.getType(appContext)!!) {
            MERCHANT -> {
              return@withContext R.menu.bottom_nav_menu3
            }
            ADMIN -> {
                return@withContext  R.menu.bottom_nav_menu2
            }
            else -> {
                return@withContext R.menu.bottom_nav_menu
            }
        }

    }


}