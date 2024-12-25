package com.umarfarid.complaintbox

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.umarfarid.complaintbox.databinding.ActivityAddComplaintBinding
import com.umarfarid.complaintbox.ui.auth.AuthViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class Add_Complaint : AppCompatActivity() {

    lateinit var binding:  ActivityAddComplaintBinding;
    lateinit var viewModel: AddCompaintViewModel
    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var currentUid="";
        authViewModel=AuthViewModel()
        authViewModel.checkUser()
        binding = ActivityAddComplaintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AddCompaintViewModel()

        lifecycleScope.launch {
            viewModel.isSuccessfullySaved.collect {
                it?.let {
                    if (it == true) {
                        Toast.makeText(
                            this@Add_Complaint,
                            "Successfully saved",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        finish()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                it?.let {
                    Toast.makeText(this@Add_Complaint, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            authViewModel.currentUser.collect{
                it?.let {
                    currentUid = it.uid
                }
            }
        }

        binding.submitButton.setOnClickListener {
            val title = binding.titleInput.text.toString().trim()
            val description = binding.descriptionInput.text.toString().trim()

            // Validate the input fields
            if (title.isEmpty() || description.isEmpty() ) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val complaint = Complaint()
            complaint.date= SimpleDateFormat("yyyy-MM-dd HH:mm a").format(System.currentTimeMillis())
            complaint.title = title
            complaint.status="Pending"
            complaint.discription = description
            complaint.userId = currentUid
            viewModel.saveComplaint(complaint)
            Toast.makeText(this, "Your Complaint is Listed Successfully!", Toast.LENGTH_SHORT).show()

        }


    }
}