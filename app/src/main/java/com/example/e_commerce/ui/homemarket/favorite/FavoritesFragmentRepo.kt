package com.example.e_commerce.ui.homemarket.favorite

import android.content.Context
import com.example.e_commerce.Constants.BEAUTY
import com.example.e_commerce.Constants.CLOTHES
import com.example.e_commerce.Constants.FOOD
import com.example.e_commerce.Constants.HOUSE_WARE
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

    private suspend fun getListOfFavorites(category : String) : List<Product> {
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
        return mutableListProducts.toList()
    }

    suspend fun getBeautyFavorites() : List<Product> = withContext(Dispatchers.IO){
        return@withContext getListOfFavorites(BEAUTY)
    }

    suspend fun getClothesFavorites() : List<Product> = withContext(Dispatchers.IO){
        return@withContext getListOfFavorites(CLOTHES)
    }

    suspend fun getFoodFavorites() : List<Product> = withContext(Dispatchers.IO){
        return@withContext getListOfFavorites(FOOD)
    }

    suspend fun getHouseWareFavorites() : List<Product> = withContext(Dispatchers.IO){
        return@withContext getListOfFavorites(HOUSE_WARE)
    }

}