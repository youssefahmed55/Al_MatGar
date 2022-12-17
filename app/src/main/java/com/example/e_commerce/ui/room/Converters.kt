package com.example.e_commerce.ui.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    //Convert List Of Strings To String
    @TypeConverter
    fun fromListOfStringItemToString(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    //Convert String To List Of String
    @TypeConverter
    fun fromStringToListOfStringItem(s: String?): List<String?>? {
        return Gson().fromJson<List<String>?>(s, object : TypeToken<List<String>?>() {}.type
        )
    }
}