package com.example.e_commerce.ui.homemarket.users.users

import android.content.Context
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepo @Inject constructor(@ApplicationContext private val appContext: Context) {

    private val db  = Firebase.firestore

    suspend fun getAllUserModels(): List<UserModel> = withContext(Dispatchers.IO) {
        Network.checkConnectionType(appContext)
        return@withContext db.collection("Users").get().await().toObjects(UserModel::class.java)
    }
}