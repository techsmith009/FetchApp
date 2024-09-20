package com.example.fetchapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.fetchapp.model.FetchItem
import com.example.fetchapp.repository.FetchRepository

open class FetchViewModel : ViewModel() {
    private val _fetchItems = MutableStateFlow<List<FetchItem>>(emptyList())
    open val fetchItems: StateFlow<List<FetchItem>> = _fetchItems.asStateFlow()

    init {
        getItems()
    }

    private fun getItems() {
        val repo = FetchRepository.getFetchRepo()
        viewModelScope.launch(Dispatchers.IO) {
            val items = repo.getFetchItem()
                ?.filter { !it.name.isNullOrBlank() }
                ?.sortedWith(compareBy({ it.listId }, { it.name }))

            if (items != null) {
                _fetchItems.value = items
            }
        }
    }
}
