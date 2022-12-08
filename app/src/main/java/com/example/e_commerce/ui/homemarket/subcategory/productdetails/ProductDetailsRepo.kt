package com.example.e_commerce.ui.homemarket.subcategory.productdetails

import android.content.Context
import android.widget.Toast
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.SharedPrefsUtil
import com.example.e_commerce.utils.ToastyUtil
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductDetailsRepo @Inject constructor(@ApplicationContext private val appContext: Context) {

    private val db  = Firebase.firestore


  suspend fun getUserType() : String = withContext(Dispatchers.IO){
        return@withContext SharedPrefsUtil.getType(appContext) ?: ""
    }

    suspend fun addToCard(product: Product , count : Int) = withContext(Dispatchers.IO){
        db.collection("inCart").document(SharedPrefsUtil.getId(appContext)!!).collection("Products").document(product.id).set({null}).await()
        db.collection("inCart").document(SharedPrefsUtil.getId(appContext)!!).collection("Count").document(product.id).set(hashMapOf("count" to count)).await()
        withContext(Dispatchers.Main){ToastyUtil.successToasty(appContext,"Added To Card Successfully",Toast.LENGTH_SHORT)}
     }





}