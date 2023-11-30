package com.pvbcsoft.portfolio.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pvbcsoft.portfolio.adapter.WorksAdapter
import com.pvbcsoft.portfolio.databinding.FragmentWorksBinding
import com.pvbcsoft.portfolio.model.Works

class WorksFragment : Fragment() {

    private lateinit var binding: FragmentWorksBinding
    private lateinit var worksList: ArrayList<Works>
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerViewWorks()
    }

    private fun initRecyclerViewWorks() {
        databaseReference = FirebaseDatabase.getInstance().getReference("works")
        worksList = arrayListOf()

        fetchDataWorks()

        binding.recyclerViewWorks.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun fetchDataWorks() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                worksList.clear()
                if (snapshot.exists()) {
                    for (worksSnapshot in snapshot.children) {
                        val works = worksSnapshot.getValue(Works::class.java)

                        worksList.add(works!!)
                    }
                }
                val rvAdapter = WorksAdapter(context!!, worksList)
                binding.recyclerViewWorks.adapter = rvAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, " error : $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}