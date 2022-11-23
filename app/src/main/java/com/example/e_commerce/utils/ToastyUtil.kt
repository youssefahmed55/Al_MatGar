package com.example.e_commerce.utils

import android.content.Context
import es.dmoral.toasty.Toasty

object ToastyUtil {

    fun successToasty(context:Context , text : String,duration : Int){
        Toasty.success(context,text,duration,true).show()
    }

    fun errorToasty(context:Context , text : String,duration : Int){
        Toasty.error(context,text,duration,true).show()
    }

    fun infoToasty(context:Context , text : String,duration : Int){
        Toasty.info(context,text,duration,true).show()
    }
}