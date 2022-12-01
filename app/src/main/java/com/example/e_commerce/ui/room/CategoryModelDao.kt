package com.example.e_commerce.ui.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_commerce.pojo.Category
import com.example.e_commerce.pojo.Product


@Dao
interface CategoryModelDao {
    //Get Product Model From DataBase
    @Query("SELECT * FROM categoryTable")
    suspend fun getAllCategories(): List<Category>

    //Insert Product Model In DataBase
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(listOfCategories : List<Category>)

}