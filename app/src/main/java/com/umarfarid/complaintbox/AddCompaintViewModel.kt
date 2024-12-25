package com.umarfarid.complaintbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umarfarid.complaintbox.model.repositories.ComplaintRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddCompaintViewModel: ViewModel(){
    val complaintRepository = ComplaintRepository()
//    val storageRepository = StorageRepository()

    val isSuccessfullySaved = MutableStateFlow<Boolean?>(null)
    val failureMessage = MutableStateFlow<String?>(null)


    fun saveComplaint(complaint: Complaint) {
        viewModelScope.launch {
            val result = complaintRepository.saveComplaint(complaint)
            if (result.isSuccess) {
                isSuccessfullySaved.value = true
            } else {
                failureMessage.value = result.exceptionOrNull()?.message
            }
        }
    }

}
