package com.the.app.common.di

import android.content.Context
import com.the.app.feature.main.data.gyro.GyroDataSource
import com.the.app.feature.main.data.gyro.GyroDataSourceImpl
import com.the.app.feature.main.data.location.LocationDataSource
import com.the.app.feature.main.data.location.LocationDataSourceImpl
import com.the.app.feature.main.data.shake.ShakeDataSource
import com.the.app.feature.main.data.shake.ShakeDataSourceImpl
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

    @Singleton
    @Provides
    fun provideGyroDataSource(@ApplicationContext context: Context): GyroDataSource = GyroDataSourceImpl(context)

    @Singleton
    @Provides
    fun provideShakeDataSource(@ApplicationContext context: Context): ShakeDataSource = ShakeDataSourceImpl(context)

}