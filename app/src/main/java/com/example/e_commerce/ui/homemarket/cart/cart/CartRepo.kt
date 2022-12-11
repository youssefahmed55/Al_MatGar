package com.example.e_commerce.ui.homemarket.cart.cart

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


class CartRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore) {

    suspend fun getInCartProducts() : List<Product> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val productIdsQuery = db.collection("inCart").document(SharedPrefsUtil.getId(appContext)!!).collection("Products").get().await()
        val mutableListOfProducts = mutableListOf<Product>()
        productIdsQuery.forEach {
            val productQuery = db.collection("AllProducts").document(it.id).get().await()
            if (productQuery.exists()){
                mutableListOfProducts.add(productQuery.toObject(Product::class.java)!!)
            }
        }
        mutableListOfProducts.forEach {
            val favoriteQuery = db.collection("Favorites").document(SharedPrefsUtil.getId(appContext)!!).collection("MyFavorites").document(it.id).get().await()
            if (favoriteQuery.exists()) it.isFavorite = true
        }
        return@withContext  mutableListOfProducts.toList()
     }

    suspend fun getProductsCount(list : List<Product>) : List<Int> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val mutableList = mutableListOf<Int>()
        list.forEach {
            val countQuery = db.collection("inCart").document(SharedPrefsUtil.getId(appContext)!!).collection("Count").document(it.id).get().await()
            mutableList.add(countQuery.getLong("count")!!.toInt())
        }
        return@withContext mutableList.toList()
    }

    suspend fun deleteProductFromInCart(productId : String) = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        db.collection("inCart").document(SharedPrefsUtil.getId(appContext)!!).collection("Products").document(productId).delete().await()
        db.collection("inCart").document(SharedPrefsUtil.getId(appContext)!!).collection("Count").document(productId).delete().await()

    }





}