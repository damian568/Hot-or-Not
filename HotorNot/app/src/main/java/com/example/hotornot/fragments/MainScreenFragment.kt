package com.example.hotornot.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotornot.R
import com.example.hotornot.databinding.FragmentMainScreenBinding

const val email = "didi.milenov@gmail.com"
const val subject = "Friends"
const val message = "Damian Tsvetkov :zdr bepce ko pr"

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickButton()
        buttonVisibility()
        sendMessageToFriend()
    }

    private fun onClickButton() {
        binding.btnRight.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.stan)
            binding.txtName.text = R.string.stan.toString()
        }

        binding.btnLeft.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.georgi)
            binding.txtName.text = R.string.georgi.toString()
        }
    }

    private fun buttonVisibility() {
        if (binding.txtName.text == "Georgi") binding.btnLeft.visibility
        else if (binding.txtName.text == "Stan") binding.btnRight.visibility
    }

    private fun sendMessageToFriend() {
        binding.email.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)
                putExtra(Intent.EXTRA_STREAM, Uri.fromParts("mailto", email, null))
            }
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }
    }
}