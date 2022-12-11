package com.example.e_commerce.ui.homemarket.account

import android.content.Context
import android.net.Uri
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AccountRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore) {
    val userModel = SharedPrefsUtil.getUserModel(appContext)

    suspend fun changeGender(gender : String) = withContext(Dispatchers.IO){
        if (userModel?.gender != gender) {
            db.collection("Users").document(userModel?.id!!).update("gender", gender).await()
            saveNewDataInShared()
        }
        }

    suspend fun changeBirthday(birthday : String)= withContext(Dispatchers.IO){
        if (userModel?.birthday != birthday) {
            db.collection("Users").document(userModel?.id!!).update("birthday", birthday).await()
            saveNewDataInShared()
        }
    }

    suspend fun changeEmail(newEmail : String , password: String) : DefaultStates = withContext(Dispatchers.IO){
        if (userModel?.email != newEmail) {
            if (userModel?.signInWithGoogle == false) {
                val user = FirebaseAuth.getInstance().currentUser
                val credential = EmailAuthProvider.getCredential(userModel.email!!, password)
                user?.reauthenticate(credential)?.await()
                FirebaseAuth.getInstance().currentUser?.updateEmail(newEmail)?.await()
                FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().await()

                db.collection("Users").document(userModel.id!!).update("email", newEmail).await()
                saveNewDataInShared()
                return@withContext DefaultStates.Success("signInAgain")
            }else{
                throw Exception(appContext.getString(R.string.You_canot_change_the_email_or_password_of_Google_account))
            }
        }else{
            return@withContext DefaultStates.Success("Saved Successfully")
        }

    }

    suspend fun changePhone(phone : String)= withContext(Dispatchers.IO){
        if (userModel?.phone != phone) {
            db.collection("Users").document(userModel?.id!!).update("phone", phone).await()
            saveNewDataInShared()
        }
    }

    suspend fun changePassword(newPassword : String , password: String)= withContext(Dispatchers.IO){
        if (userModel?.signInWithGoogle == false) {
            val user = FirebaseAuth.getInstance().currentUser
            val credential = EmailAuthProvider.getCredential(userModel.email!!, password)
            user?.reauthenticate(credential)?.await()
            FirebaseAuth.getInstance().currentUser?.updatePassword(newPassword)?.await()
        }else{ throw Exception(appContext.getString(R.string.You_canot_change_the_email_or_password_of_Google_account)) }





    }

    suspend fun changeLocation(location : String)= withContext(Dispatchers.IO){
        if (userModel?.location != location) {
            db.collection("Users").document(userModel?.id!!).update("location", location).await()
            saveNewDataInShared()
        }
    }

    suspend fun changeProfileImage(imageUri : Uri)= withContext(Dispatchers.IO){
        val storageRef= FirebaseStorage.getInstance().getReference("Users").child(userModel?.id!!).child("ProfileImage").child(userModel.id)

        val image = storageRef.putFile(imageUri)
            .await() // await() instead of snapshot
            .storage
            .downloadUrl
            .await() // await the url
            .toString()

            db.collection("Users").document(userModel.id).update("image",image).await()
            saveNewDataInShared()
    }

    private suspend fun saveNewDataInShared() = withContext(Dispatchers.IO){
        val user = db.collection("Users").document(userModel?.id!!).get().await().toObject(UserModel::class.java)
        SharedPrefsUtil.saveUserModel(appContext,user!!)
    }


}