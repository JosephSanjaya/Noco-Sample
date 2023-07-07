package com.example.myapplication.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.myapplication.api.StringNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Modules {
    @Provides
    @Singleton
    fun providesNetwork(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.201:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideStringNetwork(retrofit: Retrofit): StringNetwork {
        return retrofit.create(StringNetwork::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("default", MODE_PRIVATE)
    }
}