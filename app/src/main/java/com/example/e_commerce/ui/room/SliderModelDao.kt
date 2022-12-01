package com.example.e_commerce.ui.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_commerce.pojo.SliderModel


@Dao
interface SliderModelDao {
    //Get Product Model From DataBase
    @Query("SELECT * FROM sliderTable")
    suspend fun getAllSliderModels(): List<SliderModel>

    //Insert Product Model In DataBase
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSliderModels(listOfSliderModels : List<SliderModel>)

}