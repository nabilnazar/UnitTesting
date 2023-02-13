package com.meghamlabs.unittesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.meghamlabs.unittesting.getOrAwaitValueTest
import com.meghamlabs.unittesting.other.Constants
import com.meghamlabs.unittesting.other.Status
import com.meghamlabs.unittesting.reposiories.FakeShoppingRepositories

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShoppingViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ShoppingViewModel


    @Before
    fun setup(){
            viewModel = ShoppingViewModel(FakeShoppingRepositories())
    }

    @Test
    fun `insert shopping item with empty field returns error`(){

        viewModel.insertShoppingItem("name","","3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }


    @Test
    fun `insert shopping item with too long name, returns error`(){

        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1){
                    append(1)
            }
        }

        viewModel.insertShoppingItem(string,"5","3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert shopping item with too long price, returns error`(){

        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1){
                    append(1)
            }
        }

        viewModel.insertShoppingItem("name","5",string)

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }


    @Test
    fun `insert shopping item with too high amount, returns error`(){

        viewModel.insertShoppingItem("name","9999999999999999999999999999999","3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }


    @Test
    fun `insert shopping item with valid input, returns success`(){

        viewModel.insertShoppingItem("name","9","3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)

    }





}


