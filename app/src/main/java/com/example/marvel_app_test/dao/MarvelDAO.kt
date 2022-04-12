package com.example.marvel_app_test.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvel_app_test.model.Characters

@Dao
interface MarvelDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: Characters)

    @Query("SELECT * FROM Characters ORDER BY name" )
    fun allCharacters() : List<Characters>

    @Query("SELECT * FROM Characters WHERE name LIKE '%' || :query || '%'")
    fun fetchFiltered(query : String) : List<Characters>

}