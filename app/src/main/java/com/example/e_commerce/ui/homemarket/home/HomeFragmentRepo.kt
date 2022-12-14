package com.example.e_commerce.ui.homemarket.home

import android.content.Context
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Category
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.pojo.SliderModel
import com.example.e_commerce.ui.room.CategoryModelDao
import com.example.e_commerce.ui.room.ProductModelDao
import com.example.e_commerce.ui.room.SliderModelDao
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class HomeFragmentRepo @Inject constructor(@ApplicationContext private val appContext: Context , private val categoryModelDao: CategoryModelDao , private val productModelDao: ProductModelDao , private val sliderModelDao: SliderModelDao, private val db : FirebaseFirestore) {

    //Get Slider Products Images
    suspend fun getSliderProductsImages() = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val listOfSliders = db.collection("Slider").get().await().toObjects(SliderModel::class.java)
        sliderModelDao.insertAllSliderModels(listOfSliders)
    }
    //Get Product By Id
    suspend fun getProduct(productId : String) : Product = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val productQuery = db.collection("AllProducts").document(productId).get().await()
        if (productQuery.exists()){
            val product = productQuery.toObject(Product::class.java)!!
            val favoriteQuery = db.collection("Favorites").document(SharedPrefsUtil.getId(appContext)!!).collection("MyFavorites").document(product.id).get().await()
            if (favoriteQuery.exists()) product.isFavorite = true //Check If Is It Favorite To User
            return@withContext product
        }else{
            throw Exception(appContext.getString(R.string.This_Product_Not_Available_Now))
        }

    }
    //Get Categories
    suspend fun getCategories()  = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val categories = db.collection("Categories").get().await().toObjects(Category::class.java)
        categoryModelDao.insertAllCategories(categories)
    }
    //Get Products By Category Type
    suspend fun getProductsByCategoryType(type : String) = withContext(Dispatchers.IO){
        Network.checkConnectionType(appContext)
        val myRandomValues = MutableList(2) { Random.nextInt(0, 2) }
        var listOfProducts = db.collection("AllProducts").whereEqualTo("category",type).whereIn("randomValue",myRandomValues).limit(5).get().await().toObjects(Product::class.java)
        if (listOfProducts.size < 4)
            listOfProducts = db.collection("AllProducts").whereEqualTo("category",type).limit(5).get().await().toObjects(Product::class.java)
        productModelDao.deleteAllProductsByType(type)
        productModelDao.insertAllProducts(listOfProducts)
    }

    //ROOM
    //Get Categories From Room DataBase
    suspend fun getCategoriesDataBase() : List<Category> = withContext(Dispatchers.IO){
        return@withContext categoryModelDao.getAllCategories()
    }

    //Get Products By Category Type From Room DataBase
    suspend fun getProductsDataBaseByCategoryType(type : String) : List<Product> = withContext(Dispatchers.IO){
        return@withContext productModelDao.getProductModelsByType(type)
    }
    //Get Sliders From Room DataBase
    suspend fun getSlidersDataBase() : List<SliderModel> = withContext(Dispatchers.IO){
        return@withContext sliderModelDao.getAllSliderModels()
    }
}