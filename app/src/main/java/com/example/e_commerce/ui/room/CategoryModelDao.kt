package com.example.e_commerce.ui.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_commerce.pojo.Category


@Dao
interface CategoryModelDao {
    //Get List Of Category From DataBase
    @Query("SELECT * FROM categoryTable")
    suspend fun getAllCategories(): List<Category>

    //Insert List Of Category In DataBase
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(listOfCategories : List<Category>)

}