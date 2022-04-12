package com.example.marvel_app_test.di

import android.content.Context
import com.example.marvel_app_test.dao.AppDatabase
import com.example.marvel_app_test.dao.MarvelDAO
import com.example.marvel_app_test.repository.MarvelRepository
import com.example.marvel_app_test.services.MarvelService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesMarvel(retrofit: Retrofit) : MarvelService =
        retrofit.create(MarvelService::class.java)

    @Provides
    fun getDao( @ApplicationContext context: Context) : MarvelDAO{
        return AppDatabase.getDatabase(context).marvelDAO()
    }

    @Provides
    fun providesRepository(service : MarvelService, dao : MarvelDAO, @ApplicationContext context: Context) : MarvelRepository=
        MarvelRepository(service,dao,context)

}
