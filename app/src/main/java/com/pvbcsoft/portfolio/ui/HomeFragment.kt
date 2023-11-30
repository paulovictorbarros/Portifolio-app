package com.pvbcsoft.portfolio.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pvbcsoft.portfolio.databinding.FragmentHomeBinding
import com.pvbcsoft.portfolio.utils.LinkUtils

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseReference: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getStartData(LinkUtils(requireContext()))

        Handler(
            Looper
                .getMainLooper()
        )
            .postDelayed({ progressHome() }, 3000)
    }

    private fun getStartData(openLink: LinkUtils) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Start")
        databaseReference.child("profile").get().addOnSuccessListener {
            if (it.exists()) {
                val startLogo = it.child("startProfileImage").value
                val startName = it.child("startNameProfile").value
                val startDesc = it.child("startDescProfile").value
                val startCurriculumBtn = it.child("startCurriculumBtn").value

                binding.homeItem.apply {
                    tvStartNameProfile.text = startName.toString()
                    tvStartDescProfile.text = startDesc.toString()

                    Glide.with(root.context).load(startLogo)
                        .into(ivStartProfileImage)

                    btnStartCurriculum.setOnClickListener {
                        val link = startCurriculumBtn.toString()
                        openLink.openLink(link)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun progressHome() {

        binding.progressHome.visibility = View.GONE
        binding.layoutHome.visibility = View.VISIBLE
    }
}