package com.example.e_commerce.ui.homemarket.myproducts.orders

import android.content.Context
import com.example.e_commerce.Constants.CANCELED
import com.example.e_commerce.Constants.COMPLETED
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Order
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrdersRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore)  {
    //Get Orders By Status
    suspend fun getOrdersByStatus(status : String) : List<Order> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        return@withContext db.collection("merchantOrders").document(SharedPrefsUtil.getId(appContext)!!).collection("Orders").whereEqualTo("status",status).get().await().toObjects(Order::class.java)
    }
    //Get Product From FireStore By Id
    suspend fun getProduct(productId : String) : Product = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val productQuery = db.collection("AllProducts").document(productId).get().await()
        if (productQuery.exists()){
            return@withContext productQuery.toObject(Product::class.java)!!
        }else{
            throw Exception(appContext.getString(R.string.This_Product_Not_Available_Now))
        }
    }
    //Set Order Completed
    suspend fun setOrderCompleted(orderId: String, customerId : String) = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        db.collection("merchantOrders").document(SharedPrefsUtil.getId(appContext)!!).collection("Orders").document(orderId).update("status",COMPLETED).await()
        db.collection("customerOrders").document(customerId).collection("Orders").document(orderId).update("status",COMPLETED).await()
        setOrderEndDate(orderId,customerId)
    }
    //Set Order Canceled
    suspend fun setOrderCanceled(orderId: String, customerId : String) = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        db.collection("merchantOrders").document(SharedPrefsUtil.getId(appContext)!!).collection("Orders").document(orderId).update("status",CANCELED).await()
        db.collection("customerOrders").document(customerId).collection("Orders").document(orderId).update("status",CANCELED).await()
        setOrderEndDate(orderId,customerId)
    }
    //Set Order EndDate After Complete Or Cancel It
    private suspend fun setOrderEndDate(orderId: String, customerId : String) = withContext(Dispatchers.IO){
        db.collection("merchantOrders").document(SharedPrefsUtil.getId(appContext)!!).collection("Orders").document(orderId).update("end_timeStamp",FieldValue.serverTimestamp()).await()
        db.collection("customerOrders").document(customerId).collection("Orders").document(orderId).update("end_timeStamp",FieldValue.serverTimestamp()).await()
    }


}