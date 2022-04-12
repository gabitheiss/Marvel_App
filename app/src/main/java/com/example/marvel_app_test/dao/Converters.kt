package com.example.marvel_app_test.dao

import androidx.room.TypeConverter
import com.example.marvel_app_test.model.Thumbnail

class Converters {

    @TypeConverter
    fun thumbnailToString(value : Thumbnail) : String?{
        return "${value.path}.${value.extension}"
    }

    @TypeConverter
    fun stringToThumbnail(value: String) : Thumbnail{
        val string = value.split(".")
        return Thumbnail(string[0], string[1])
    }
}