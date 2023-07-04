package com.example.cybtest.di

import android.content.Context
import com.example.cybtest.api.ApiService
import com.example.cybtest.api.ComicsApiRepo
import com.example.cybtest.connectivity.ConnectivityMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun provideApiRepo() = ComicsApiRepo(ApiService.api)


    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context) =
        ConnectivityMonitor.getInstance(context)
}