package com.example.mangochat.di

import android.content.Context
import com.example.data.pref.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun providePreferences(
        @ApplicationContext appContext: Context,
    ): Preferences = Preferences(appContext)
}
