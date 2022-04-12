package com.example.marvel_app_test.model

import androidx.room.*

data class CharacterModel(
    @Embedded
     val data: Data
)

data class Data(
    @Embedded
    val results: List<Characters>
)

@Entity
data class Characters(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")  val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @Embedded val thumbnail: Thumbnail
)

data class Thumbnail(
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "extension") val extension: String
) {
    fun concatImage(): String {
        if(path.contains("image_not_available")){
            return "https://i.pinimg.com/originals/39/21/a9/3921a902bbff06c14e64757795607bae.jpg"
        }else{
            return "$path.$extension"
        }
    }
}
