package com.androiddevs.shoppinglisttestingyt.di

import android.content.Context
import androidx.room.Room
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingDao
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItemDatabase
import com.androiddevs.shoppinglisttestingyt.data.remote.PixabayAPI
import com.androiddevs.shoppinglisttestingyt.others.Constants
import com.androiddevs.shoppinglisttestingyt.repositories.DefaultRepository
import com.androiddevs.shoppinglisttestingyt.repositories.ShoppingRepo
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
    fun providerShoppingItemDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, ShoppingItemDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideDefaultShoppingRepo(dao: ShoppingDao, api: PixabayAPI) = DefaultRepository(dao,api) as ShoppingRepo

    @Singleton
    @Provides
    fun provideShoppingDao(database: ShoppingItemDatabase) = database.shoppingDao()


    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
}