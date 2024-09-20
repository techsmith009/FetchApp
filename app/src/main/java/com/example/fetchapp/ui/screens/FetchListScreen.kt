package com.example.fetchapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fetchapp.viewmodel.FetchViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fetchapp.R
import com.example.fetchapp.model.FetchItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FetchListScreen(modifier: Modifier, viewModel: FetchViewModel = viewModel()) {
    val fetchItems by viewModel.fetchItems.collectAsState()

    Column() {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.fetch_list),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Cyan)
        )

        if (fetchItems.isEmpty()) {
            CenteredIndicator()
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                val groupedItems = fetchItems.groupBy { it.listId }

                groupedItems.forEach { (listId, items) ->
                    item {
                        Text(
                            text = "List ID: $listId",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    items(items) { item ->
                        FetchItemRow(item)
                    }
                }
            }
        }
      }
}

@Composable
fun CenteredIndicator() {
    // Use Box to center the indicator on the screen
    Box(
        modifier = Modifier
            .fillMaxSize(), // Fill the entire screen
        contentAlignment = Alignment.Center // Align content to the center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .semantics {
                    contentDescription = "Loading Indicator"
                }// Adjust size if necessary
        )
    }
}

@Composable
fun FetchItemRow(item: FetchItem) {
    // Wrap the Row in a Card for better visual presentation
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // External padding for spacing between items
         // Elevation for shadow effect
        shape = RoundedCornerShape(12.dp) // Rounded corners
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Internal padding for space inside the card
            verticalAlignment = Alignment.CenterVertically // Align items vertically
        ) {
            // Create a circular icon with the first two letters of the name or digits
            Box(
                modifier = Modifier
                    .size(50.dp) // Larger size for better visibility
                    .background(Color(0xFF6200EA), CircleShape), // Purple circular background
                contentAlignment = Alignment.Center // Center the text inside the circle
            ) {
                val itemNumber = item.name?.split(" ")?.filter { it.all { char -> char.isDigit() } }
                    ?.joinToString(separator = " ") ?: "NA"
                Text(
                    text = itemNumber,
                    color = Color.White, // White text on a purple background
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, // Make it bold
                    textAlign = TextAlign.Center
                )
            }

            // Spacer between the "image" and text for separation
            Spacer(modifier = Modifier.width(16.dp))

            // Column with List ID and Name text
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "List ID: ${item.listId}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Blue // Blue color for List ID
                    )
                )
                Spacer(modifier = Modifier.height(4.dp)) // Spacing between List ID and Name
                Text(
                    text = "Name: ${item.name}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Gray // Gray color for the name
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FetchListScreen(modifier = Modifier.fillMaxSize())
}
