package com.example.marvel_app_test.services

import com.example.marvel_app_test.BuildConfig
import com.example.marvel_app_test.model.CharacterModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {

    @GET("/v1/public/characters")
    suspend fun getAllCharacterToApi(@Query("ts") ts : String,
                                     @Query("apikey") apikey : String = BuildConfig.KEY_API,
                                     @Query("hash") hash : String,
                                     @Query("series") series : Int? = 24229,
                                     @Query("limit") limit : String?,
                                     @Query("offset") offset : Int) : Response<CharacterModel>

}
