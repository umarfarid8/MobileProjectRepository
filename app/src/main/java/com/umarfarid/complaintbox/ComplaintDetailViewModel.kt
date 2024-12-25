package com.umarfarid.complaintbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umarfarid.complaintbox.model.repositories.ComplaintRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ComplaintDetailViewModel:ViewModel() {
    val isSaving = MutableStateFlow<Boolean?>(false)
    val isSaved = MutableStateFlow<Boolean?>(null)
    val isFailure = MutableStateFlow<String?>(null)
    val complaintRepository = ComplaintRepository()

    val failureMessage = MutableStateFlow<String?>(null)


    fun update(complaint: Complaint) {
        viewModelScope.launch {
            isSaving.value=true
            val result = complaintRepository.updateComplaint(complaint)
            if (result.isSuccess) {
                isSaved.value = true
                isSaving.value= false
            } else {
                isSaving.value= false
                isFailure.value = result.exceptionOrNull()?.message
            }
        }
    }

}