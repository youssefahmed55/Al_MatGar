package com.example.e_commerce.ui.homemarket.favorite

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

class FavoritesFragmentRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore) {

    private suspend fun getListOfFavoritesId(): List<String> = withContext(Dispatchers.IO) {
        val favoriteQuery = db.collection("Favorites").document(SharedPrefsUtil.getId(appContext)!!).collection("MyFavorites").get().await()
        val mutableFavoriteList = mutableListOf<String>()
        favoriteQuery.forEach { mutableFavoriteList.add(it.id)}
        return@withContext mutableFavoriteList.toList()
    }

    suspend fun getListOfFavoritesByCategory(category : String) : List<Product> = withContext(Dispatchers.IO) {
        Network.checkConnectionType(appContext)
        val favoriteList = getListOfFavoritesId()
        val mutableListProducts = mutableListOf<Product>()
        favoriteList.forEach {
            val productDoc = db.collection("AllProducts").document(it).get().await()
            if (productDoc.exists()){
                val product = productDoc.toObject(Product::class.java)
                if(product?.category == category) { product.isFavorite = true ; mutableListProducts.add(product) }
            }
        }
        return@withContext mutableListProducts.toList()
    }

}