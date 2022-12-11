package com.example.e_commerce.ui.homemarket.explore

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

class ExploreRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore) {

    suspend fun getBeauty() : List<Product> = withContext(Dispatchers.IO){
        return@withContext getProductsByType(BEAUTY)
    }

    suspend fun getClothes() : List<Product> = withContext(Dispatchers.IO){
        return@withContext getProductsByType(CLOTHES)
    }

    suspend fun getFood() : List<Product> = withContext(Dispatchers.IO){
        return@withContext getProductsByType(FOOD)
    }

    suspend fun getHouseWare() : List<Product> = withContext(Dispatchers.IO){
        return@withContext getProductsByType(HOUSE_WARE)
    }

    private suspend fun getProductsByType(type:String) : List<Product> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val listOfProducts = db.collection("AllProducts").whereEqualTo("category", type).get().await().toObjects(
          Product::class.java)
        listOfProducts.forEach {
            val favoriteQuery = db.collection("Favorites").document(SharedPrefsUtil.getId(appContext)!!).collection("MyFavorites").document(it.id).get().await()
            if (favoriteQuery.exists()) it.isFavorite = true
        }
        return@withContext listOfProducts
    }

}