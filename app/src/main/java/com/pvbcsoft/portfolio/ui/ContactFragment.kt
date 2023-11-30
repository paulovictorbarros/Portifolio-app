package com.pvbcsoft.portfolio.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pvbcsoft.portfolio.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contact()
        socialMedia()
    }

    private fun contact() {
        binding.btnContactSend.setOnClickListener {

            val recipient = "paulovictorbarrosdecarvalho@gmail.com"
            val subject = binding.tiContactSubject.text.toString().trim()
            val message = binding.tiContactMessage.text.toString().trim()

            val intent = Intent(Intent.ACTION_SEND)

            if (subject.isNotEmpty() && message.isNotEmpty()) {
                intent.type = "message/rfc822"

                intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                intent.putExtra(Intent.EXTRA_TEXT, message)
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))

                try {
                    startActivity(Intent.createChooser(intent, "Enviar E-mail"))
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }

                Handler().postDelayed({
                    binding.tiContactSubject.text!!.clear()
                    binding.tiContactMessage.text!!.clear()
                }, 5000)
            } else {
                Toast.makeText(
                    requireContext(), "Por favor, preencha os campos", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun socialMedia() {
        databaseReference = FirebaseDatabase.getInstance().getReference("SocialMedia")
        databaseReference.child("Links").get().addOnSuccessListener {
            if (it.exists()) {
                val linkedinLink = it.child("linkedinLink").value
                val githubLink = it.child("githubLink").value
                val instagramLink = it.child("instagramLink").value

                binding.ivLinkedin.setOnClickListener {
                    openLink(linkedinLink.toString())
                }

                binding.ivGithub.setOnClickListener {
                    openLink(githubLink.toString())
                }

                binding.ivInstagram.setOnClickListener {
                    openLink(instagramLink.toString())
                }
            }
        }
    }

    private fun openLink(link: String) {
        val uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, uri)

        startActivity(intent)
    }
}