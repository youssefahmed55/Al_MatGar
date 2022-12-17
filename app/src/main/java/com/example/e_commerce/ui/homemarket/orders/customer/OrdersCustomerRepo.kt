package com.example.e_commerce.ui.homemarket.orders.customer

import android.content.Context
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Order
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrdersCustomerRepo @Inject constructor(@ApplicationContext private val appContext: Context , private val db : FirebaseFirestore)  {
    //Get Orders Of Customer
   suspend fun getOrders() : List<Order> = withContext(Dispatchers.IO){
       Network.checkConnectionType(appContext)
       val orders = db.collection("customerOrders").document(SharedPrefsUtil.getId(appContext)!!).collection("Orders").get().await().toObjects(Order::class.java)
       orders.forEach {
           val favoriteQuery = db.collection("Favorites").document(SharedPrefsUtil.getId(appContext)!!).collection("MyFavorites").document(it.productId).get().await()
           if (favoriteQuery.exists()) it.isFavorite = true //Check If Is It Favorite To User
       }
       return@withContext orders
   }
    //get Product From FireStore
    suspend fun getProduct(productId : String) : Product = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val productQuery = db.collection("AllProducts").document(productId).get().await()
        if (productQuery.exists()){
            val product = productQuery.toObject(Product::class.java)!!
            val favoriteQuery = db.collection("Favorites").document(SharedPrefsUtil.getId(appContext)!!).collection("MyFavorites").document(product.id).get().await()
            if (favoriteQuery.exists()) product.isFavorite = true //Check If Is It Favorite To User
            return@withContext product
        }else{
            throw Exception(appContext.getString(R.string.This_Product_Not_Available_Now))
        }

    }


}