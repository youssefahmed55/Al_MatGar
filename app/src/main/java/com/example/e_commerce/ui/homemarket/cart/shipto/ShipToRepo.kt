package com.example.e_commerce.ui.homemarket.cart.shipto

import android.content.Context
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Order
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ShipToRepo @Inject constructor(@ApplicationContext private val appContext: Context){
    private val db  = Firebase.firestore
    suspend fun getUserModelData() : UserModel = withContext(Dispatchers.IO){
        return@withContext SharedPrefsUtil.getUserModel(appContext)!!
    }

    suspend fun createOrder(listOfProducts : List<Product>, listOfCounts : List<Int>) : DefaultStates = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val customerId = SharedPrefsUtil.getId(appContext)!!
        for (i in listOfProducts.indices){
        val docId =  db.collection("customerOrders").document().id
        db.collection("customerOrders").document(customerId).collection("Orders").document(docId).set(Order(docId,listOfProducts[i].id,listOfProducts[i].merchantId!!,customerId,listOfCounts[i])).await()
        db.collection("merchantOrders").document(listOfProducts[i].merchantId!!).collection("Orders").document(docId).set(Order(docId,listOfProducts[i].id,listOfProducts[i].merchantId!!,customerId,listOfCounts[i])).await()
        db.collection("inCart").document(customerId).collection("Products").document(listOfProducts[i].id).delete().await()
        db.collection("inCart").document(customerId).collection("Count").document(listOfProducts[i].id).delete().await()
       }
        return@withContext DefaultStates.Success(appContext.getString(R.string.success))
    }
}