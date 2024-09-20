package com.example.fetchapp

import com.example.fetchapp.model.FetchItem
import com.example.fetchapp.viewmodel.FetchViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeEmptyFetchViewModel : FetchViewModel() {
    private val _fetchItems = MutableStateFlow<List<FetchItem>>(emptyList())
    override val fetchItems: StateFlow<List<FetchItem>> = _fetchItems.asStateFlow()
}

class FakeFetchViewModel(private val testItems: List<FetchItem>) : FetchViewModel() {
    private val _fetchItems = MutableStateFlow(testItems)
    override val fetchItems: StateFlow<List<FetchItem>> = _fetchItems.asStateFlow()
}