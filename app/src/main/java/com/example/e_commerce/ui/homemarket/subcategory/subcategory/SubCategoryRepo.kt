package com.example.e_commerce.ui.homemarket.subcategory.subcategory

import android.content.Context
import com.example.e_commerce.Constants.BEAUTY
import com.example.e_commerce.Constants.ELECTRONICS
import com.example.e_commerce.Constants.FOOD
import com.example.e_commerce.Constants.HOUSE_WARE
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class SubCategoryRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore) {
    //Get All Products By Type Id
    suspend fun getAllProducts(categoryId : Int) : List<Product> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val categoryName = when(categoryId){ 1 -> BEAUTY  2 -> ELECTRONICS 3 -> FOOD 4 -> HOUSE_WARE else -> ""}
        val randomIndex = Random.nextInt(2)
        val products = db.collection("AllProducts").whereEqualTo("category",categoryName).get().await().toObjects(Product::class.java)
        products.forEach {
            val favoriteQuery = db.collection("Favorites").document(SharedPrefsUtil.getId(appContext)!!).collection("MyFavorites").document(it.id).get().await()
            if (favoriteQuery.exists()) it.isFavorite = true   //Check If Is It Favorite To User
        }
        //This Way To Change Arrange Of Products
        val productsSorted = mutableListOf<Product>()
        when (randomIndex) {
            0 -> {
                for (i in 0 until 2) products.forEach { if (it.randomValue == i) productsSorted.add(it) }
            }
            1-> {
                for (i in 1 downTo 0) products.forEach { if (it.randomValue == i) productsSorted.add(it) }
            }
        }

        return@withContext productsSorted.toList()
    }
}