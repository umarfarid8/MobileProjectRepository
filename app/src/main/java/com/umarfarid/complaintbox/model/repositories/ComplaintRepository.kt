package com.umarfarid.complaintbox.model.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.umarfarid.complaintbox.Complaint
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class ComplaintRepository {
    val complaintCollection = FirebaseFirestore.getInstance().collection("complaints")

    suspend fun saveComplaint(complaint: Complaint): Result<Boolean> {
        try {
            val document = complaintCollection.document()
            complaint.comid = document.id
            document.set(complaint).await()
            return Result.success(true)

        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun updateComplaint(complaint: Complaint): Result<Boolean> {
        try {
            if (complaint.comid.isNullOrEmpty()) {
                return Result.failure(IllegalArgumentException("Order ID (rid) is null or empty"))
            }
            complaintCollection.document(complaint.comid!!).set(complaint).await()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    fun getAllComplaints() =
        complaintCollection.snapshots().map { it.toObjects(Complaint::class.java) }

    fun getComplaintsOfUser(userId: String) =
        complaintCollection.whereEqualTo("userId",userId).snapshots().map { it.toObjects(Complaint::class.java)
        }
}

