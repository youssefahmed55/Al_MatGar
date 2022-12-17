package com.example.e_commerce.ui.homemarket.offer

import android.content.Context
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfferRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore) {
    //Get Products Offers By Type
    suspend fun getProductsOffersByType(type : String) : List<Product> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val listOfProducts = db.collection("AllProducts").whereEqualTo("hasOffer",true).whereEqualTo("category", type).get().await().toObjects(Product::class.java)
        listOfProducts.forEach {
            val favoriteQuery = db.collection("Favorites").document(SharedPrefsUtil.getId(appContext)!!).collection("MyFavorites").document(it.id).get().await()
            if (favoriteQuery.exists()) it.isFavorite = true //Check If Is It Favorite To User
        }
        return@withContext listOfProducts
    }

}