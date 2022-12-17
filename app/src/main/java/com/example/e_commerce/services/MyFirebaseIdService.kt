package com.example.e_commerce.services

import com.example.e_commerce.Constants.ADMIN
import com.example.e_commerce.Constants.ANONYMOUS
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseIdService:FirebaseMessagingService(){

    @Inject
    lateinit var db : FirebaseFirestore

    override fun onNewToken(s:String){
        super.onNewToken(s)
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val type = SharedPrefsUtil.getType(this)
        if (type != null){
            if (type != ADMIN && type != ANONYMOUS){
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@addOnCompleteListener
                    }
                    if (task.result != null) {
                        val token: String = task.result
                        if(firebaseUser!=null){
                            if (!firebaseUser.isAnonymous)
                                updateToken(token) //Update Token
                        }
                    }
                }
            }
        }


    }
    private fun updateToken(refreshToken:String){
        db.collection("Tokens").document(FirebaseAuth.getInstance().currentUser!!.uid).set(hashMapOf("token" to refreshToken)) //Update Token in Firestore
    }

}