package com.example.e_commerce.ui.homemarket.home

import android.content.Context
import com.example.e_commerce.Constants.BEAUTY
import com.example.e_commerce.Constants.CLOTHES
import com.example.e_commerce.Constants.FOOD
import com.example.e_commerce.Constants.HOUSE_WARE
import com.example.e_commerce.pojo.Category
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.pojo.SliderModel
import com.example.e_commerce.ui.room.CategoryModelDao
import com.example.e_commerce.ui.room.ProductModelDao
import com.example.e_commerce.ui.room.SliderModelDao
import com.example.e_commerce.utils.Network
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class HomeFragmentRepo @Inject constructor(@ApplicationContext private val appContext: Context , private val categoryModelDao: CategoryModelDao , private val productModelDao: ProductModelDao , private val sliderModelDao: SliderModelDao) {

    private val db  = Firebase.firestore
    private val myRandomValues = MutableList(2) { Random.nextInt(0, 2) }

    suspend fun getSliderProductsImages() = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val listOfSliders = db.collection("Slider").get().await().toObjects(SliderModel::class.java)
        sliderModelDao.insertAllSliderModels(listOfSliders)
    }

    suspend fun getCategories()  = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val categories = db.collection("Categories").get().await().toObjects(Category::class.java)
        categoryModelDao.insertAllCategories(categories)
    }

    suspend fun getBeautyProducts() = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        var listOfProducts = db.collection("AllProducts").whereEqualTo("category",BEAUTY).whereIn("randomValue",myRandomValues).limit(5).get().await().toObjects(Product::class.java)
        if (listOfProducts.size < 4)
            listOfProducts = db.collection("AllProducts").whereEqualTo("category",BEAUTY).limit(5).get().await().toObjects(Product::class.java)
        productModelDao.deleteAllProductsByType(BEAUTY)
        productModelDao.insertAllProducts(listOfProducts)
    }

    suspend fun getFoodProducts() = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        var listOfProducts = db.collection("AllProducts").whereEqualTo("category",FOOD).whereIn("randomValue",myRandomValues).limit(5).get().await().toObjects(Product::class.java)
        if (listOfProducts.size < 4)
            listOfProducts = db.collection("AllProducts").whereEqualTo("category",FOOD).limit(5).get().await().toObjects(Product::class.java)
        productModelDao.deleteAllProductsByType(FOOD)
        productModelDao.insertAllProducts(listOfProducts)
    }

    suspend fun getClothesProducts() = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        var listOfProducts = db.collection("AllProducts").whereEqualTo("category",CLOTHES).whereIn("randomValue",myRandomValues).limit(5).get().await().toObjects(Product::class.java)
        if (listOfProducts.size < 4)
            listOfProducts = db.collection("AllProducts").whereEqualTo("category",CLOTHES).limit(5).get().await().toObjects(Product::class.java)
        productModelDao.deleteAllProductsByType(CLOTHES)
        productModelDao.insertAllProducts(listOfProducts)
    }

    suspend fun getHouseWareProducts() = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        var listOfProducts = db.collection("AllProducts").whereEqualTo("category",HOUSE_WARE).whereIn("randomValue",myRandomValues).limit(5).get().await().toObjects(Product::class.java)
        if (listOfProducts.size < 4)
            listOfProducts = db.collection("AllProducts").whereEqualTo("category",HOUSE_WARE).limit(5).get().await().toObjects(Product::class.java)
        productModelDao.deleteAllProductsByType(HOUSE_WARE)
        productModelDao.insertAllProducts(listOfProducts)
    }

    //ROOM

    suspend fun getCategoriesDataBase() : List<Category> = withContext(Dispatchers.IO){
        return@withContext categoryModelDao.getAllCategories()
    }

    //
    suspend fun getBeautyProductsDataBase() : List<Product> = withContext(Dispatchers.IO){
        return@withContext productModelDao.getProductModelsByType(BEAUTY)
    }

    suspend fun getFoodProductsDataBase() : List<Product> = withContext(Dispatchers.IO){
        return@withContext productModelDao.getProductModelsByType(FOOD)
    }

    suspend fun getClothesProductsDataBase() : List<Product> = withContext(Dispatchers.IO){
        return@withContext productModelDao.getProductModelsByType(CLOTHES)
    }

    suspend fun getHouseWareProductsDataBase() : List<Product> = withContext(Dispatchers.IO){
        return@withContext productModelDao.getProductModelsByType(HOUSE_WARE)
    }

    suspend fun getSlidersDataBase() : List<SliderModel> = withContext(Dispatchers.IO){
        return@withContext sliderModelDao.getAllSliderModels()
    }
}