package com.umarfarid.complaintbox.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umarfarid.complaintbox.model.repositories.HandCraftRepository
import com.umarfarid.complaintbox.ui.HandCraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {
    val handCraftsRepository = HandCraftRepository()

    val failureMessage = MutableStateFlow<String?>(null)
    val data = MutableStateFlow<List<HandCraft>?>(null)

    init {
        readHandcrafts()
    }

    fun readHandcrafts() {
        viewModelScope.launch {
            handCraftsRepository.getHandCrafts().catch {
                failureMessage.value = it.message
            }
                .collect {
                    data.value = it
                }
        }
    }
}