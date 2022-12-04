package com.example.e_commerce.ui.homemarket.offer

import android.content.Context
import com.example.e_commerce.Constants
import com.example.e_commerce.Constants.BEAUTY
import com.example.e_commerce.Constants.CLOTHES
import com.example.e_commerce.Constants.FOOD
import com.example.e_commerce.Constants.HOUSE_WARE
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.Network
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfferRepo @Inject constructor(@ApplicationContext private val appContext: Context) {

    private val db  = Firebase.firestore

    suspend fun getBeautyOffers() : List<Product> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        return@withContext db.collection("AllProducts").whereEqualTo("hasOffer",true).whereEqualTo("category", BEAUTY).get().await().toObjects(Product::class.java)
    }

    suspend fun getClothesOffers() : List<Product> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        return@withContext db.collection("AllProducts").whereEqualTo("hasOffer",true).whereEqualTo("category", CLOTHES).get().await().toObjects(Product::class.java)
    }

    suspend fun getFoodOffers() : List<Product> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        return@withContext db.collection("AllProducts").whereEqualTo("hasOffer",true).whereEqualTo("category", FOOD).get().await().toObjects(Product::class.java)
    }

    suspend fun getHouseWareOffers() : List<Product> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        return@withContext db.collection("AllProducts").whereEqualTo("hasOffer",true).whereEqualTo("category", HOUSE_WARE).get().await().toObjects(Product::class.java)
    }

}