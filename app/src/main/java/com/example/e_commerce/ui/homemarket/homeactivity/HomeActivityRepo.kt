package com.example.e_commerce.ui.homemarket.homeactivity

import android.content.Context
import com.example.e_commerce.BuildConfig
import com.example.e_commerce.Constants.ADMIN
import com.example.e_commerce.Constants.ANONYMOUS
import com.example.e_commerce.Constants.MERCHANT
import com.example.e_commerce.R
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.example.e_commerce.utils.SignedInUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeActivityRepo @Inject constructor(@ApplicationContext private val appContext: Context,private val db : FirebaseFirestore) {
    //Get Image URL
    suspend fun getImageUrl(): String = withContext(Dispatchers.IO) {
        return@withContext SharedPrefsUtil.getImageUrl(appContext)!!
    }
    //Get Welcome Message
    suspend fun getWelcomeMessage(): String = withContext(Dispatchers.IO) {
        val name = SharedPrefsUtil.getName(appContext)!!
        if (SignedInUtil.getIsSignIn(appContext)){
            if (name.isNotEmpty())
            return@withContext appContext.getString(R.string.welcome_back) + ", " + SharedPrefsUtil.getName(appContext)
            else return@withContext appContext.getString(R.string.welcome_back)
        }else{
            SignedInUtil.setIsSignIn(appContext,true)
            if (name.isNotEmpty())
            return@withContext appContext.getString(R.string.welcome) + ", " + SharedPrefsUtil.getName(appContext)
            else return@withContext appContext.getString(R.string.welcome)
        }
    }
   //Get Menu By Type Of User
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
    //Update Token
    suspend fun updateMyToken() = withContext(Dispatchers.IO){
        val type = SharedPrefsUtil.getType(appContext)!!
        if (type != ANONYMOUS && type != ADMIN){
            val token = FirebaseMessaging.getInstance().token.await()
            if (token != null && token.isNotEmpty()) {
                db.collection("Tokens").document(SharedPrefsUtil.getId(appContext)!!)
                    .set(hashMapOf("token" to token.toString())).await()
            }
        }
    }
   //Remove Token
   suspend fun removeMyToken() = withContext(Dispatchers.IO){
       Network.checkConnectionType(appContext)
       val type = SharedPrefsUtil.getType(appContext)!!
       if (type != ANONYMOUS && type != ADMIN)
       db.collection("Tokens").document(SharedPrefsUtil.getId(appContext)!!).delete().await()
       }
    //Sign Out
    suspend fun signOut() = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        FirebaseAuth.getInstance().signOut()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_API_KEY)
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(appContext, gso)
        googleClient.signOut()
        SharedPrefsUtil.clearUserModel(appContext)
        SignedInUtil.setIsSignIn(appContext,false)
    }


}