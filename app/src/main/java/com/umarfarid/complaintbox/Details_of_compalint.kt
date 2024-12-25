package com.umarfarid.complaintbox


import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.umarfarid.complaintbox.databinding.ActivityDetailsOfComplaintBinding
import com.umarfarid.complaintbox.ui.auth.AuthViewModel
import kotlinx.coroutines.launch

class Details_of_compalint : AppCompatActivity() {
    lateinit var binding: ActivityDetailsOfComplaintBinding
    lateinit var complaint:Complaint
    lateinit var viewModel:ComplaintDetailViewModel
    lateinit var progressDialog: ProgressDialog
    lateinit var  authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel= ComplaintDetailViewModel()

        binding= ActivityDetailsOfComplaintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var isAdmin = false
        if (AuthRepository().getCurrentUser()?.email.equals("ahmadzhair2003@gmail.com"))
            isAdmin = true


        complaint = Gson().fromJson(intent.getStringExtra("data"),Complaint::class.java)

        binding.textView31.text = complaint.title?.toString()
        binding.textView32.text = complaint.discription?.toString()
        binding.textView28.text = complaint.date?.toString()
        binding.namee.text = complaint.status?.toString()


        progressDialog= ProgressDialog(this)
        progressDialog.setMessage("Updating the Status...Please Update")
        progressDialog.setCancelable(false)
        authViewModel= AuthViewModel()

        binding.completedd2.visibility = View.GONE
        binding.completedd4.visibility = View.GONE
        binding.completedd3.visibility = View.GONE
        binding.completedd.visibility = View.GONE


        binding.completedd.setOnClickListener {
            complaint.status="Issue Solved"
            viewModel.update(complaint)
            progressDialog.show()
        }
        if((isAdmin && complaint.status.equals("Pending")) || (isAdmin && complaint.status.equals("Issue Not Solved"))){
            binding.completedd.visibility = View.VISIBLE
        }
        if(!isAdmin && complaint.status.equals("Issue Solved")){
            binding.completedd4.visibility = View.VISIBLE
            binding.completedd3.visibility = View.VISIBLE
        }

        binding.completedd4.setOnClickListener {
            complaint.status="Issue Not Solved"
            viewModel.update(complaint)
            progressDialog.show()
        }
        binding.completedd3.setOnClickListener {
            complaint.status="Solved"
            viewModel.update(complaint)
            progressDialog.show()
        }


        lifecycleScope.launch {
            viewModel.isSaving.collect {
//                progressDialog.show()
                if (it==true) {
                    Toast.makeText(
                        this@Details_of_compalint,
                        "Status Updated Successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    progressDialog.dismiss()
                    finish()

                }
            }
        }

        lifecycleScope.launch {
            viewModel.isFailure.collect {
                it?.let {
                    Toast.makeText(this@Details_of_compalint, it, Toast.LENGTH_LONG).show()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                it?.let {
                    Toast.makeText(this@Details_of_compalint, it, Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}