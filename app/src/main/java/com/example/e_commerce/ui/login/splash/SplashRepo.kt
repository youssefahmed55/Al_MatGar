package com.example.e_commerce.ui.login.splash

import android.content.Context
import com.example.e_commerce.R
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SplashRepo @Inject constructor(@ApplicationContext private val appContext: Context) {


    suspend fun signInAnonymously () : DefaultStates = withContext(Dispatchers.IO) {
            FirebaseAuth.getInstance().signInAnonymously().await()
            SharedPrefsUtil.saveUserModel(appContext, UserModel(FirebaseAuth.getInstance().currentUser?.uid, type = "Anonymous"))
            return@withContext DefaultStates.Success(appContext.getString(R.string.Sign_In_Anonymous_Successfully))
        }


}