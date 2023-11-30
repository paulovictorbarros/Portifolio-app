package com.pvbcsoft.portfolio.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pvbcsoft.portfolio.adapter.SkillsAdapter
import com.pvbcsoft.portfolio.databinding.FragmentSkillsBinding
import com.pvbcsoft.portfolio.model.Skills

class SkillsFragment : Fragment() {

    private lateinit var binding: FragmentSkillsBinding
    private lateinit var skillsList: ArrayList<Skills>
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSkillsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        initRecyclerViewSkills()
    }

    private fun initRecyclerViewSkills() {
        databaseReference = FirebaseDatabase.getInstance().getReference("technologies")
        skillsList = arrayListOf()

        fetchDataSkills()

        binding.recyclerViewSkills.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun fetchDataSkills() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                skillsList.clear()
                if (snapshot.exists()) {
                    for (skillsSnap in snapshot.children) {
                        val skills = skillsSnap.getValue(Skills::class.java)
                        skillsList.add(skills!!)
                    }
                }
                val rvAdapter = SkillsAdapter(skillsList)
                binding.recyclerViewSkills.adapter = rvAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, " error : $error", Toast.LENGTH_SHORT).show()
            }

        })
    }
}