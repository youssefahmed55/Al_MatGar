package com.example.e_commerce.ui.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_commerce.pojo.Product


@Dao
interface ProductModelDao {
    //Get Products Model From DataBase
    @Query("SELECT * FROM productsTable Where category Like :type")
    suspend fun getProductModelsByType(type : String): List<Product>

    //Insert Products Model In DataBase
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(listOfProducts : List<Product>)

    //Delete All Products From Table
    @Query("DELETE FROM productsTable Where category Like :type")
    suspend fun deleteAllProductsByType(type : String)

}