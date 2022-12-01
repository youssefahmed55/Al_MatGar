package com.example.e_commerce.ui.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.e_commerce.pojo.Category
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.pojo.SliderModel


@Database(entities = [Product::class,Category::class,SliderModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productModelDao(): ProductModelDao
    abstract fun categoryModelDao() : CategoryModelDao
    abstract fun sliderModelDao() : SliderModelDao
}