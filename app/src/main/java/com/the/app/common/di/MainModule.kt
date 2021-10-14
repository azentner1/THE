package com.the.app.common.di

import android.content.Context
import com.the.app.common.repo.LocationDataSource
import com.the.app.common.repo.LocationDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideLocationDataSource(@ApplicationContext context: Context): LocationDataSource = LocationDataSourceImpl(context)

}