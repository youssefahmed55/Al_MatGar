package com.example.e_commerce.ui.homemarket.home

import android.content.Context
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Category
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.pojo.SliderModel
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.example.e_commerce.utils.SignedInUtil
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class HomeFragmentRepo @Inject constructor(@ApplicationContext private val appContext: Context) {

    private val db  = Firebase.firestore
    private val myRandomValues = MutableList(2) { Random.nextInt(0, 2) }

    suspend fun getSliderProductsImages() : MutableList<String> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val sliders = db.collection("Slider").get().await().toObjects(SliderModel::class.java)
        val mutableListOfImages = mutableListOf<String>()
        sliders.forEach {
            mutableListOfImages.add(it.image!!)
        }
        return@withContext mutableListOfImages
    }

    suspend fun getCategories() : List<Category> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        return@withContext db.collection("Categories").get().await().toObjects(Category::class.java)
    }

    suspend fun getBeautyProducts() : List<Product> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val listOfProducts = db.collection("AllProducts").whereEqualTo("category","Beauty").whereIn("randomValue",myRandomValues).limit(5).get().await().toObjects(Product::class.java)
        return@withContext if (listOfProducts.size > 4) listOfProducts else db.collection("AllProducts").whereEqualTo("category","Beauty").limit(5).get().await().toObjects(Product::class.java)
    }

    suspend fun getFoodProducts() : List<Product> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val listOfProducts = db.collection("AllProducts").whereEqualTo("category","Food").whereIn("randomValue",myRandomValues).limit(5).get().await().toObjects(Product::class.java)
        return@withContext if (listOfProducts.size > 4) listOfProducts else db.collection("AllProducts").whereEqualTo("category","Food").limit(5).get().await().toObjects(Product::class.java)
    }

    suspend fun getClothesProducts() : List<Product> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val listOfProducts = db.collection("AllProducts").whereEqualTo("category","Clothes").whereIn("randomValue",myRandomValues).limit(5).get().await().toObjects(Product::class.java)
        return@withContext if (listOfProducts.size > 4) listOfProducts else db.collection("AllProducts").whereEqualTo("category","Clothes").limit(5).get().await().toObjects(Product::class.java)
    }

    suspend fun getHouseWareProducts() : List<Product> = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val listOfProducts = db.collection("AllProducts").whereEqualTo("category","HouseWare").whereIn("randomValue",myRandomValues).limit(5).get().await().toObjects(Product::class.java)
        return@withContext if (listOfProducts.size > 4) listOfProducts else db.collection("AllProducts").whereEqualTo("category","HouseWare").limit(5).get().await().toObjects(Product::class.java)
    }

    suspend fun getImageUrl(): String = withContext(Dispatchers.IO) {
        return@withContext SharedPrefsUtil.getImageUrl(appContext)!!
    }

    suspend fun getWelcomeMessage(): String = withContext(Dispatchers.IO) {
        if (SignedInUtil.getIsSignIn(appContext)){
            return@withContext appContext.getString(R.string.welcome_back) + ' ' + SharedPrefsUtil.getName(appContext)

        }else{
            SignedInUtil.setIsSignIn(appContext,true)
            return@withContext appContext.getString(R.string.welcome) + ' ' + SharedPrefsUtil.getName(appContext)
        }

    }

}