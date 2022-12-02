package com.example.e_commerce.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoryTable")
data class Category(@PrimaryKey val id : Int = 0, val name : String ?= "", val image : String ?= "")
