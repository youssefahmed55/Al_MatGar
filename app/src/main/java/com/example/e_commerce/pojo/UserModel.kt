package com.example.e_commerce.pojo

import java.io.Serializable

data class UserModel(val id : String ?="" ,val fullName:String?="",val email : String?="", val type : String?="" ,  val signInWithGoogle : Boolean?= false ,val phone : String?="" , val birthday :String?="" , val gender : String?="", val location :String?="" , val image : String?="" ) :
    Serializable
