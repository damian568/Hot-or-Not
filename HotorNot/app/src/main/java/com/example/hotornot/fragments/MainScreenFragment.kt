package com.example.hotornot.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotornot.Constants
import com.example.hotornot.PreferencesUtil
import com.example.hotornot.R
import com.example.hotornot.data.FriendGenerator
import com.example.hotornot.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var preferencesUtil: PreferencesUtil
    private lateinit var friendGenerator: FriendGenerator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesUtil = PreferencesUtil.getInstance(view.context)
        showRandomImage()
        buttonsVisibility()
        sendMessageToFriend()
    }

    private fun getFriends(){
        preferencesUtil.getFriends()
    }

    private fun setFriends(){
        preferencesUtil.setFriends(friendGenerator.generateFriendList())
    }

    private fun showRandomImage() {
        binding.btnRight.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.stan)
            binding.txtName.text = R.string.stan.toString()
        }

        binding.btnLeft.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.georgi)
            binding.txtName.text = R.string.georgi.toString()
        }
    }

    private fun buttonsVisibility() {
        if (binding.txtName.text == "Georgi") binding.btnLeft.visibility
        else if (binding.txtName.text == "Stan") binding.btnRight.visibility
    }

    private fun sendMessageToFriend() {
        binding.email.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                type = Constants.TYPE_EMAIL_INTENT
                putExtra(Intent.EXTRA_EMAIL, arrayOf(Constants.EMAIL))
                putExtra(Intent.EXTRA_SUBJECT, Constants.SUBJECT)
                putExtra(Intent.EXTRA_TEXT, Constants.MESSAGE)
                putExtra(Intent.EXTRA_STREAM, Uri.fromParts("mailto", Constants.EMAIL, null))
            }
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }
    }
}