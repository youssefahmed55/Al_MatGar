package com.example.e_commerce.ui.homemarket.account

import android.content.Context
import android.net.Uri
import com.example.e_commerce.BuildConfig
import com.example.e_commerce.Constants.ADMIN
import com.example.e_commerce.Constants.ANONYMOUS
import com.example.e_commerce.Constants.FEMALE
import com.example.e_commerce.Constants.MALE
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.example.e_commerce.utils.SignedInUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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

    //Change Gender
    suspend fun changeGender(gender : String) = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val gender2 =  if (gender == MALE || gender == "ذكر"){ MALE }else{ FEMALE }
        if (userModel?.gender != gender2) {
            db.collection("Users").document(userModel?.id!!).update("gender", gender2).await()
            saveNewDataInShared() //Save New Data From FireStore
        }
        }

    //Change Birthday
    suspend fun changeBirthday(birthday : String)= withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        if (userModel?.birthday != birthday) {
            db.collection("Users").document(userModel?.id!!).update("birthday", birthday).await()
            saveNewDataInShared() //Save New Data From FireStore
        }
    }

    //Change Email
    suspend fun changeEmail(newEmail : String , password: String) : DefaultStates = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        if (userModel?.email != newEmail) {
            if (userModel?.signInWithGoogle == false) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user?.uid == userModel.id) {
                    val credential = EmailAuthProvider.getCredential(userModel.email, password)
                    user.reauthenticate(credential).await()
                    FirebaseAuth.getInstance().currentUser?.updateEmail(newEmail)?.await()
                    FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().await()

                    db.collection("Users").document(userModel.id).update("email", newEmail).await()
                    saveNewDataInShared() //Save New Data From FireStore
                    return@withContext DefaultStates.Success("signInAgain")
                }else{throw Exception(appContext.getString(R.string.Please_Sign_Out_Then_Sign_In_And_Try_Again))}
            }else{
                throw Exception(appContext.getString(R.string.You_canot_change_the_email_or_password_of_Google_account))
            }
        }else{
            return@withContext DefaultStates.Success("Saved Successfully")
        }

    }
    //Change Phone
    suspend fun changePhone(phone : String)= withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        if (userModel?.phone != phone) {
            db.collection("Users").document(userModel?.id!!).update("phone", phone).await()
            saveNewDataInShared() //Save New Data From FireStore
        }
    }

    //Change Password
    suspend fun changePassword(newPassword : String , password: String)= withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        if (userModel?.signInWithGoogle == false) {
            val user = FirebaseAuth.getInstance().currentUser
            if (user?.uid == userModel.id) {
                val credential = EmailAuthProvider.getCredential(userModel.email, password)
                user.reauthenticate(credential).await()
            }else{throw Exception(appContext.getString(R.string.Please_Sign_Out_Then_Sign_In_And_Try_Again))}
            FirebaseAuth.getInstance().currentUser?.updatePassword(newPassword)?.await()
        }else{ throw Exception(appContext.getString(R.string.You_canot_change_the_email_or_password_of_Google_account)) }

    }

    //Change Location
    suspend fun changeLocation(location : String)= withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        if (userModel?.location != location) {
            db.collection("Users").document(userModel?.id!!).update("location", location).await()
            saveNewDataInShared() //Save New Data From FireStore
        }
    }

    //Change Profile Image
    suspend fun changeProfileImage(imageUri : Uri)= withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val storageRef= FirebaseStorage.getInstance().getReference("Users").child(userModel?.id!!).child("ProfileImage").child(userModel.id)

        val image = storageRef.putFile(imageUri)
            .await() // await() instead of snapshot
            .storage
            .downloadUrl
            .await() // await the url
            .toString()

            db.collection("Users").document(userModel.id).update("image",image).await()
            saveNewDataInShared() //Save New Data From FireStore
    }

    //Save New Data From FireStore
    private suspend fun saveNewDataInShared() = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val user = db.collection("Users").document(userModel?.id!!).get().await().toObject(UserModel::class.java)
        SharedPrefsUtil.saveUserModel(appContext,user!!)
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