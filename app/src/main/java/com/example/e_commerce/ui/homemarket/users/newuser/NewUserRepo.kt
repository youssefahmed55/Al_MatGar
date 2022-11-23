package com.example.e_commerce.ui.homemarket.users.newuser

import android.content.Context
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class NewUserRepo @Inject constructor(@ApplicationContext private val appContext: Context) {

    private val db  = Firebase.firestore

    suspend fun createAccount(fullName : String, email: String, gender : Int, type : Int , birthday :String, phone: String, location :String, password: String): DefaultStates = withContext(Dispatchers.IO) {
        val type2 :String = when(type){
        1 -> appContext.getString(R.string.Customer)
        2 -> appContext.getString(R.string.Merchant)
        3 -> appContext.getString(R.string.Admin)
        else -> ""
        }

        val gender2 : String = when(gender){
            1 -> appContext.getString(R.string.male)
            2 -> appContext.getString(R.string.female)
            else -> ""
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().await()
        db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).set(UserModel(FirebaseAuth.getInstance().currentUser!!.uid,fullName,email,type2,false,phone,birthday,gender2,location)).await()

        return@withContext DefaultStates.Success(appContext.getString(R.string.Created_Account_Successfully))
    }




}