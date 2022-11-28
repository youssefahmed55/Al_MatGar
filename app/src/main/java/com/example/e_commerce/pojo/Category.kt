package com.example.e_commerce.pojo

import java.io.Serializable


data class Category(val id : Int ?= 0 , val name : String ?= "", val image : String ?= "") : Serializable
