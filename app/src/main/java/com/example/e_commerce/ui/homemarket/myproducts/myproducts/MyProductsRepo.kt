package com.example.e_commerce.ui.homemarket.myproducts.myproducts

import android.content.Context
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyProductsRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore) {

    suspend fun getAllProductModels(): List<Product> = withContext(Dispatchers.IO) {
        Network.checkConnectionType(appContext)
        val  querySnapshots = db.collection("MyProduct").document(getUserId()).collection("Products").get().await()
        val mutableListIdsOfProducts = mutableListOf<String>()
        querySnapshots.forEach{
             mutableListIdsOfProducts.add(it.id)
        }
        if(mutableListIdsOfProducts.size == 0) return@withContext emptyList()
        else
        return@withContext db.collection("AllProducts").whereIn("id", mutableListIdsOfProducts).get().await().toObjects(Product::class.java)
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

    private fun getUserId():String = SharedPrefsUtil.getId(appContext)!!

}