package com.example.e_commerce.ui.login.signup

import android.content.Context
import com.example.e_commerce.Constants.CUSTOMER
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.DefaultStates
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore) {

    //Create User
    suspend fun createUserFireBase (email : String , password : String, phone : String, fullName : String) : DefaultStates =
        withContext(Dispatchers.IO) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await() //FirebaseAuth Create User With Email And Password
            FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().await()           //Send Email Verification
            db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).set(UserModel(FirebaseAuth.getInstance().currentUser!!.uid,fullName,email,CUSTOMER,false,phone)).await() //Save User Model In FireStore
            return@withContext DefaultStates.Success(appContext.getString(R.string.Please_Check_Your_Email_to_Verification_Make_Sure_To_Check_Spam_Messages))
        }


}