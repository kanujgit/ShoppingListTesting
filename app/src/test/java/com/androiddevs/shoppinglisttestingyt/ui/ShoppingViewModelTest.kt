package com.androiddevs.shoppinglisttestingyt.ui

import com.androiddevs.shoppinglisttestingyt.getOrAwaitValueTest
import com.androiddevs.shoppinglisttestingyt.others.Constants
import com.androiddevs.shoppinglisttestingyt.others.Status
import com.androiddevs.shoppinglisttestingyt.repositories.FakeShoppingRepository
import com.google.common.truth.Truth
import junit.framework.TestCase
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test


class ShoppingViewModelTest : TestCase() {
    private lateinit var viewModel: ShoppingViewModel

    @BeforeAll
    fun setup() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty field and return error`() {
        viewModel.insertShoppingItem("name", "", "300")
        val value = viewModel.insertShoppingItem.getOrAwaitValueTest()
        Truth.assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name and return error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_LENGTH + 1)
                append(1)
        }
        viewModel.insertShoppingItem(string, "5", "300")
        val value = viewModel.insertShoppingItem.getOrAwaitValueTest()
        Truth.assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount and return error`() {
        viewModel.insertShoppingItem("string", "5555555555555", "")
        val value = viewModel.insertShoppingItem.getOrAwaitValueTest()
        Truth.assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input and return true`() {
        viewModel.insertShoppingItem("string", "4", "4.4")
        val value = viewModel.insertShoppingItem.getOrAwaitValueTest()
        Truth.assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}