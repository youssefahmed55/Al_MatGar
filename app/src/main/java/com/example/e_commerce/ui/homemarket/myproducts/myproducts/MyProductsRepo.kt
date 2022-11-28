package com.example.e_commerce.ui.homemarket.myproducts.myproducts

import android.content.Context
import android.util.Log
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyProductsRepo @Inject constructor(@ApplicationContext private val appContext: Context) {

    private val db  = Firebase.firestore

    suspend fun getAllProductModels(): List<Product> = withContext(Dispatchers.IO) {
        Network.checkConnectionType(appContext)
        try {
            val  querySnapshots = db.collection("MyProduct").document(getUserId()).collection("Products").get().await()
            val idsOfProducts = async {
                val mutableList = mutableListOf<String>()
                querySnapshots.forEach{
                    mutableList.add(it.id)
                }
                mutableList
            }
            return@withContext db.collection("AllProducts").whereIn("id", idsOfProducts.await()).get().await().toObjects(Product::class.java)
        }catch (e : Exception){
            return@withContext emptyList()
        }

    }

    suspend fun deleteProduct(productId : String , list: List<String>?) = withContext(Dispatchers.IO) {
        Network.checkConnectionType(appContext)
        db.collection("MyProduct").document(getUserId()).collection("Products").document(productId).delete().await()
        db.collection("AllProducts").document(productId).delete().await()

        list?.let { list ->
            list.forEach {
                FirebaseStorage.getInstance().getReferenceFromUrl(it).delete().await()
            }
        }



    }

    suspend fun getImageUrl(): String = withContext(Dispatchers.IO) {
        return@withContext SharedPrefsUtil.getImageUrl(appContext) ?: ""
    }

    private fun getUserId():String = SharedPrefsUtil.getId(appContext)!!

}