package com.example.e_commerce.ui.homemarket.subcategory.productdetails

import android.content.Context
import android.widget.Toast
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.example.e_commerce.utils.ToastyUtil
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductDetailsRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore) {
   //Get User Type
   suspend fun getUserType() : String = withContext(Dispatchers.IO){
        return@withContext SharedPrefsUtil.getType(appContext) ?: ""
    }
    //Add To Card
    suspend fun addToCard(product: Product , count : Int) = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val productQuery = db.collection("AllProducts").document(product.id).get().await()
        if (productQuery.exists()) {
            db.collection("inCart").document(SharedPrefsUtil.getId(appContext)!!)
                .collection("Products").document(product.id).set({ null }).await()
            db.collection("inCart").document(SharedPrefsUtil.getId(appContext)!!)
                .collection("Count").document(product.id).set(hashMapOf("count" to count)).await()
            withContext(Dispatchers.Main) { ToastyUtil.successToasty(appContext, appContext.getString(R.string.Added_To_Cart), Toast.LENGTH_SHORT) } //Toast Successful Message
        } else{
            withContext(Dispatchers.Main) { ToastyUtil.infoToasty(appContext, appContext.getString(R.string.This_Product_Not_Available_Now), Toast.LENGTH_SHORT) } //Toast Info
        }
     }







}