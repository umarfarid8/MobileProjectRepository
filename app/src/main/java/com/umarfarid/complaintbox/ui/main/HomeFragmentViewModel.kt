package com.umarfarid.complaintbox.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umarfarid.complaintbox.Complaint
import com.umarfarid.complaintbox.model.repositories.ComplaintRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {
    val complaintRepository = ComplaintRepository()

    val failureMessage = MutableStateFlow<String?>(null)
    val data = MutableStateFlow<List<Complaint>?>(null)

    init {
        readComplaints()
    }

    fun readComplaints() {
        viewModelScope.launch {
            complaintRepository.getAllComplaints().catch {
                failureMessage.value = it.message
            }
                .collect {
                    data.value = it
                }
        }
    }
}