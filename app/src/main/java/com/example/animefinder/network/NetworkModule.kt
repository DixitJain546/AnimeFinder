package com.example.animefinder.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit = Retrofit.Builder().baseUrl("https://api.jikan.moe/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): IAnimeApiService = retrofit.create(IAnimeApiService::class.java)
}