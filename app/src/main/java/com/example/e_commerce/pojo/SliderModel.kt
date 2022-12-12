package com.example.e_commerce.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "sliderTable")
data class SliderModel(@PrimaryKey val id : Int = 0, val image : String = "" , val productId : String = "")
