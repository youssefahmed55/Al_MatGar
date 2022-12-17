package com.example.e_commerce.ui.homemarket.cart.shipto

import android.content.Context
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Order
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.pojo.notification.NotificationModel
import com.example.e_commerce.pojo.notification.RootModel
import com.example.e_commerce.sendNotificationApi.APIService
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.example.e_commerce.utils.ShipToUtil
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ShipToRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore, private val apiService: APIService){
    //Get User Model From SharedPreference DataBase
    suspend fun getUserModelData() : UserModel = withContext(Dispatchers.IO){
        return@withContext SharedPrefsUtil.getUserModel(appContext)!!
    }
    //Create Orders
    suspend fun createOrder(listOfProducts : List<Product>, listOfCounts : List<Int>) : DefaultStates = withContext(Dispatchers.IO){
        val checkUserDataRequired = ShipToUtil.checkIfDataRequiredAvailable(SharedPrefsUtil.getLocation(appContext)!!,SharedPrefsUtil.getPhone(appContext)!!)
        if (checkUserDataRequired == R.string.success) {
            Network.checkConnectionType(appContext)
            val customerModel = SharedPrefsUtil.getUserModel(appContext)!!
            for (i in listOfProducts.indices) {
                val docId = db.collection("customerOrders").document().id
                val product = listOfProducts[i]
                val totalPrice =
                    if (product.hasOffer) product.offerPrice * listOfCounts[i] else product.price * listOfCounts[i]
                val order = Order(
                    docId,
                    listOfProducts[i].id,
                    totalPrice,
                    product.name,
                    product.merchantId,
                    customerModel.id,
                    customerModel.phone,
                    customerModel.location,
                    customerModel.fullName,
                    listOfCounts[i],
                    product.images?.get(0),
                )
                db.collection("customerOrders").document(customerModel.id).collection("Orders")
                    .document(docId).set(order).await()
                db.collection("merchantOrders").document(listOfProducts[i].merchantId)
                    .collection("Orders").document(docId).set(order).await()
                db.collection("inCart").document(customerModel.id).collection("Products")
                    .document(product.id).delete().await()
                db.collection("inCart").document(customerModel.id).collection("Count")
                    .document(product.id).delete().await()

                //Send Notification
                val userToken = db.collection("Tokens").document(product.merchantId).get().await()
                if (userToken.exists()){
                    val token = userToken.getString("token")
                    apiService.sendNotification(RootModel(token!!, NotificationModel(appContext.getString(R.string.You_Have_A_New_Order),"${SharedPrefsUtil.getName(appContext)} " +appContext.getString(R.string.requested_a_new_order_From_You) + "\n"+ appContext.getString(R.string.Product_Name) +": ${product.name}"+ "\n"+ appContext.getString(R.string.quantity) +": ${listOfCounts[i]}")))
                }
            }
            return@withContext DefaultStates.Success(appContext.getString(R.string.success))
        }else{
            return@withContext DefaultStates.Error(appContext.getString(checkUserDataRequired))
        }
    }

}