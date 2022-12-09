package com.example.e_commerce.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "productsTable")
data class Product(
    @PrimaryKey val id : String ="", val name : String ?="", val category : String ?= "", val brand : String ?= "", val merchantId : String ?= "", val description : String ?="", val price : Double ?= 0.0, val images : List<String> ?= emptyList(), val hasOffer : Boolean = false, val offerPrice : Double ?= 0.0, val delivery_time : Int ?= 1, val randomValue : Int = 0, var isFavorite : Boolean ?= false) :
    Serializable
