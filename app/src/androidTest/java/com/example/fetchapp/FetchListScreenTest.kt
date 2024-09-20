package com.example.fetchapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fetchapp.model.FetchItem
import com.example.fetchapp.ui.screens.CenteredIndicator
import com.example.fetchapp.ui.screens.FetchItemRow
import com.example.fetchapp.ui.screens.FetchListScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FetchListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingState() {
        // Set the screen content with an empty list (loading state)
        composeTestRule.setContent {
            FetchListScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = FakeEmptyFetchViewModel()
            )
        }

        // Check that the loading indicator is shown
        composeTestRule.onNodeWithContentDescription("Loading Indicator")
            .assertIsDisplayed()
    }

    @Test
    fun testFetchListScreenWithData() {
        val testItems = listOf(
            FetchItem(id = 1, listId = 101, name = "Alice 42"),
            FetchItem(id = 2, listId = 101, name = "Bob 24"),
            FetchItem(id = 3, listId = 102, name = "Charlie 15")
        )

        // Set content with a fake ViewModel returning test items
        composeTestRule.setContent {
            FetchListScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = FakeFetchViewModel(testItems)
            )
        }

        // Verify the names of the items are displayed
        composeTestRule.onNodeWithText("Name: Alice 42").assertIsDisplayed()
        composeTestRule.onNodeWithText("Name: Bob 24").assertIsDisplayed()
        composeTestRule.onNodeWithText("Name: Charlie 15").assertIsDisplayed()
    }

    @Test
    fun testCenteredIndicator() {
        composeTestRule.setContent {
            CenteredIndicator()
        }

        // Check if the CircularProgressIndicator is displayed
        composeTestRule.onNodeWithContentDescription("Loading Indicator")
            .assertIsDisplayed()
    }

    @Test
    fun testFetchItemRow() {
        val fetchItem = FetchItem(id = 1, listId = 101, name = "Alice 42")

        composeTestRule.setContent {
            FetchItemRow(item = fetchItem)
        }

        // Check that the List ID and Name are displayed
        composeTestRule.onNodeWithText("List ID: 101").assertIsDisplayed()
        composeTestRule.onNodeWithText("Name: Alice 42").assertIsDisplayed()

        // Check that the item number (from name) is displayed in the circular icon
        composeTestRule.onNodeWithText("42").assertIsDisplayed()
    }
}