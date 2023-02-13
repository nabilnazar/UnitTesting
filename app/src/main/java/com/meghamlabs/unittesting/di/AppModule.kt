package com.meghamlabs.unittesting.di

import android.content.Context
import androidx.room.Room
import com.meghamlabs.unittesting.data.local.ShoppingDao
import com.meghamlabs.unittesting.data.local.ShoppingItemDatabase
import com.meghamlabs.unittesting.data.remote.PixabayApi
import com.meghamlabs.unittesting.other.Constants.BASE_URL
import com.meghamlabs.unittesting.other.Constants.DATABASE_NAME
import com.meghamlabs.unittesting.repositories.DefaultShoppingRepository
import com.meghamlabs.unittesting.repositories.ShoppingRepository
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
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context,ShoppingItemDatabase::class.java,DATABASE_NAME).build()

    @Singleton
    @Provides
    fun ProvideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayApi
    )= DefaultShoppingRepository(dao,api) as ShoppingRepository

    @Singleton
    @Provides
    fun provideShoppingDao(database: ShoppingItemDatabase) = database.shoppingDao()


    @Singleton
    @Provides
    fun providePixabayApi() : PixabayApi {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayApi::class.java)
    }


}