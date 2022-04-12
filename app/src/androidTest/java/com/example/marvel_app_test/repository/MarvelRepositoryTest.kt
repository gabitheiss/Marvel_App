package com.example.marvel_app_test.repository

import com.example.marvel_app_test.model.CharacterModel
import com.example.marvel_app_test.model.Characters
import com.example.marvel_app_test.services.MarvelService
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class MarvelRepositoryTest : TestCase() {

    @Mock
    private lateinit var listCharacters : List<Characters>
    @Mock
    private lateinit var repository : MarvelRepository
    @Mock
    private lateinit var returnSucess : Response<CharacterModel>
    @Mock
    private lateinit var api : MarvelService

    private var RESULT = UNDEFINED

    companion object {
        private const val SUCCESS = 1
        private const val FAILURE = -1
        private const val UNDEFINED = 0
    }

    @Test
    fun testFetchCaracterList(): Unit = runBlocking {
        launch {
            Mockito.`when`(returnSucess.isSuccessful).thenReturn(true)
            Mockito.`when`(api.getAllCharacterList("1", "d1cdc2432c0edbbf4fb84e16a4c223ff", "6e318f797225953bb75aa3121080501a", 0))
                .thenReturn(returnSucess)
            repository.fetchCaracterList(0)
            assertThat(RESULT).isEqualTo(SUCCESS)
        }
    }

    fun testFetchFeaturedCaracter() {}

    fun testInsertToDatabase() {}

    fun testFetchAllFromDatabase() {}

    fun testSearchCharacter() {}
}

