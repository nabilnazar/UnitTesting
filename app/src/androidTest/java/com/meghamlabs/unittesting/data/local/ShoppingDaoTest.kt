package com.meghamlabs.unittesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.meghamlabs.unittesting.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    lateinit var dao: ShoppingDao

    @Before
    fun setup(){
       hiltRule.inject()
        dao = database.shoppingDao()
    }


    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlocking{
        val shoppingItem = ShoppingItem("name",10,23.5f,"url",1)
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)


    }

    @Test
    fun deleteShoppingItem() = runBlocking {
        val shoppingItem = ShoppingItem("name",10,23.5f,"url",1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).doesNotContain(shoppingItem)

    }

    @Test
    fun observeTotalPriceSum() = runBlocking {

        val shoppingItem1 = ShoppingItem("name",10,23.5f,"url")
        val shoppingItem2= ShoppingItem("vase",10,23.5f,"url")
        val shoppingItem3 = ShoppingItem("dddd",10,23.5f,"url")
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPriceSum).isEqualTo(3*(10*23.5f))
    }

}