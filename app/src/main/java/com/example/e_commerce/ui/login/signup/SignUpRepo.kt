package com.example.e_commerce.ui.login.signup

import android.content.Context
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.ui.login.LoginStates
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpRepo @Inject constructor(@ApplicationContext private val appContext: Context) {

    private val db  = Firebase.firestore
    suspend fun createUserFireBase (email : String , password : String, phone : String, fullName : String) : LoginStates =
        withContext(Dispatchers.IO) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().await()
            db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("Info").document(FirebaseAuth.getInstance().currentUser!!.uid).set(UserModel(FirebaseAuth.getInstance().currentUser!!.uid,fullName,email,"Customer",phone,"","","","")).await()
            return@withContext LoginStates.Success(appContext.getString(R.string.Please_Check_Your_Email_to_Verification_Make_Sure_To_Check_Spam_Messages))
        }


}