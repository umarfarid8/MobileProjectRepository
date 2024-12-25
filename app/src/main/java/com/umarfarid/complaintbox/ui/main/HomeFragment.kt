package com.umarfarid.complaintbox.ui.main

import ComplaintAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.umarfarid.complaintbox.Add_Complaint
import com.umarfarid.complaintbox.Complaint
import com.umarfarid.complaintbox.R

import com.umarfarid.complaintbox.databinding.FragmentHomeBinding
import com.umarfarid.complaintbox.ui.auth.AuthViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {


    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeFragmentViewModel

    lateinit var adapter: ComplaintAdapter
    lateinit var authViewModel: AuthViewModel
    lateinit var CurrentUser: FirebaseUser
    val items=ArrayList<Complaint>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= HomeFragmentViewModel()
        viewModel.readComplaints()

        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                it?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        adapter= ComplaintAdapter(items)
        binding.recyclerview.adapter=adapter
        binding.recyclerview.layoutManager= LinearLayoutManager(context)
        lifecycleScope.launch {
            viewModel.data.collect {
                it?.let {
                    items.clear()
                    items.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.data.collect {
                it?.let {
                    Toast.makeText(context, it.size.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}

