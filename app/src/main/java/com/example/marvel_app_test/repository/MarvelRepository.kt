package com.example.marvel_app_test.repository

import android.content.Context
import com.example.marvel_app_test.dao.MarvelDAO
import com.example.marvel_app_test.utils.isConnected
import com.example.marvel_app_test.model.Characters
import com.example.marvel_app_test.services.MarvelService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val service: MarvelService,
    private val marvelDAO: MarvelDAO,
    private val context: Context
) {

    suspend fun checkConnectForGetCharactersListFromDatabaseOrApi(offset: Int) : List<Characters>?{
        return if (context.isConnected()){
            fetchCaracterList(offset)
        }else{
            fetchListFromDatabase()
        }
    }

    suspend fun checkConnectForGetFeaturedCaracterFromDatabaseOrApi() : List<Characters>?{
        return if (context.isConnected()){
            fetchFeaturedCaracter()
        }else{
            fetchFeaturedFromDatabase()
        }
    }

    private suspend fun fetchFeaturedCaracter(): List<Characters>? {
        return withContext(Dispatchers.Default) {
            val response = service.getAllCharacterToApi(
                "1", "d1cdc2432c0edbbf4fb84e16a4c223ff", "6e318f797225953bb75aa3121080501a", 24229, "5", 0)
            val dataProcessed = processData(response)
            dataProcessed?.data?.results?.let{
                insertToDatabase(it)
            }
            dataProcessed?.data?.results
        }
    }

    suspend fun fetchCaracterList(offset: Int): List<Characters>? {
        return withContext(Dispatchers.Default) {
            val response = service.getAllCharacterToApi(
                "1", "d1cdc2432c0edbbf4fb84e16a4c223ff", "6e318f797225953bb75aa3121080501a", null, null, offset)
            val dataProcessed = processData(response)
            dataProcessed?.data?.results?.let {
                insertToDatabase(it)
            }
            dataProcessed?.data?.results
        }
    }

    private fun <T> processData(response: Response<T>): T? {
        return if (response.isSuccessful) response.body()
        else null
    }

    private fun insertToDatabase(items: List<Characters>) {
        items.forEach { characters ->
            marvelDAO.insert(characters = characters)
        }
    }

    fun fetchAllFromDatabase(): List<Characters> {
        return marvelDAO.allCharacters()
    }

    private fun fetchListFromDatabase(): List<Characters> {
        return marvelDAO.allCharacters().subList(6,500)
    }

    private fun fetchFeaturedFromDatabase(): List<Characters> {
        return marvelDAO.allCharacters().subList(0,4)
    }

    fun searchCharacter(query: String): List<Characters> {
        return marvelDAO.fetchFiltered(query.lowercase())
    }
}


