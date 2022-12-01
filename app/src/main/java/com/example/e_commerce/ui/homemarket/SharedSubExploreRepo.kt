package com.example.e_commerce.ui.homemarket

import android.content.Context
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SharedSubExploreRepo @Inject constructor(@ApplicationContext private val appContext: Context) {


    /*suspend fun getNextTenProducts(categoryId : Int) : List<Product> = withContext(Dispatchers.IO){
        if (lastDocumentSnapshot != null){
            Network.checkConnectionType(appContext)
            val categoryName = when(categoryId){ 1 -> "Beauty"  2 -> "Clothes" 3 -> "Food" 4 -> "HouseWare" else -> ""}
            val querySnapshots =  db.collection("AllProducts").whereEqualTo("category",categoryName).startAfter(lastDocumentSnapshot).limit(10).get().await()
            lastDocumentSnapshot = if (querySnapshots.documents.size >= 1) {querySnapshots.documents[querySnapshots.size() - 1]} else{null}
            return@withContext querySnapshots.toObjects(Product::class.java)
        }else{
            return@withContext emptyList()
        }
    }*/

    /* suspend fun getAllProducts(categoryId : Int) : List<Product> = withContext(Dispatchers.IO){
         Network.checkConnectionType(appContext)
         lastDocumentSnapshot = null
         val categoryName = when(categoryId){ 1 -> "Beauty"  2 -> "Clothes" 3 -> "Food" 4 -> "HouseWare" else -> ""}

         val productsQuerySnapshots =  db.collection("AllProducts").whereEqualTo("category",categoryName).limit(10).get().await()
         lastDocumentSnapshot = if (productsQuerySnapshots.documents.size >= 1) {productsQuerySnapshots.documents[productsQuerySnapshots.size() - 1]} else{null}

         return@withContext productsQuerySnapshots.toObjects(Product::class.java)
     }*/


}