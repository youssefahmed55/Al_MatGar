package com.example.e_commerce.ui.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_commerce.pojo.SliderModel


@Dao
interface SliderModelDao {
    //Get List Of SliderModel From DataBase
    @Query("SELECT * FROM sliderTable")
    suspend fun getAllSliderModels(): List<SliderModel>

    //Insert List Of SliderModel In DataBase
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSliderModels(listOfSliderModels : List<SliderModel>)

}