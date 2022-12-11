package com.example.e_commerce.ui.login.signin

import android.content.Context
import android.util.Log
import com.example.e_commerce.Constants.CUSTOMER
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SignInRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore) {

    suspend fun signInWithEmailAndPassword(email:String , password : String) : DefaultStates = withContext(Dispatchers.IO){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        if (FirebaseAuth.getInstance().currentUser!!.isEmailVerified) {
            val userModel = getUserModelFromFireStore()
            SharedPrefsUtil.saveUserModel(appContext,userModel)
            return@withContext DefaultStates.Success(appContext.getString(R.string.Sign_In_Successfully))
        } else {
            throw Exception(appContext.getString(R.string.Please_Check_Your_Email_to_Verification_Make_Sure_To_Check_Spam_Messages))
        }

        }

    suspend fun signInWithGoogle(idToken : String) : DefaultStates =
        withContext(Dispatchers.IO){
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val userModel : UserModel
            FirebaseAuth.getInstance().signInWithCredential(credential).await()
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            val isNotExisted = db.collection("Users").whereEqualTo("email",firebaseUser?.email).get().await().isEmpty

            Log.d("SignInRepo", "signInWithGoogle: $isNotExisted" )
            if (isNotExisted) {
                userModel = UserModel(firebaseUser?.uid,firebaseUser?.displayName,firebaseUser?.email,CUSTOMER,true)
                db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).set(userModel).await()
            }else{
                db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).update("signInWithGoogle", true).await()
                userModel = getUserModelFromFireStore()
            }
            SharedPrefsUtil.saveUserModel(appContext,userModel)
            return@withContext DefaultStates.Success(appContext.getString(R.string.Sign_In_With_Google_Account_Successfully))
        }



    private suspend fun getUserModelFromFireStore(): UserModel = withContext(Dispatchers.IO) {
             return@withContext db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).get().await().toObject(UserModel::class.java)!!
        }



}