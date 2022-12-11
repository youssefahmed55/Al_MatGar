package com.example.e_commerce.ui.homemarket.sharedrepo

import android.content.Context
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoritesRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore) {

    suspend fun addToFavorite(id : String) = withContext(Dispatchers.IO) {
        Network.checkConnectionType(appContext)
        db.collection("Favorites").document(SharedPrefsUtil.getId(appContext)!!).collection("MyFavorites").document(id).set({null}).await()
    }

    suspend fun deleteFromFavorite(id : String) = withContext(Dispatchers.IO) {
        Network.checkConnectionType(appContext)
        db.collection("Favorites").document(SharedPrefsUtil.getId(appContext)!!).collection("MyFavorites").document(id).delete().await()
    }
}