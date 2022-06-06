package com.example.hotornot.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotornot.Constants
import com.example.hotornot.PreferencesUtil
import com.example.hotornot.R
import com.example.hotornot.data.Friend
import com.example.hotornot.data.FriendGenerator
import com.example.hotornot.data.User
import com.example.hotornot.databinding.FragmentMainScreenBinding
import com.google.android.material.chip.Chip

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var preferencesUtil: PreferencesUtil
    private lateinit var friendGenerator: FriendGenerator
    private var onPauseTime = 600000

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
        friendGenerator = FriendGenerator(requireContext())
        showRandomImage()
        sendMessageToFriend()
    }

    override fun onPause() {
        super.onPause()
        onPauseTime = System.currentTimeMillis().toInt()
    }

    override fun onResume() {
        super.onResume()
        checkInactiveTime()
    }

    private fun checkInactiveTime(){
        if ((System.currentTimeMillis() - onPauseTime) > 5000){
            goToMotivationScreen()
        }
    }

    private fun goToMotivationScreen() {
        val action =
            MainScreenFragmentDirections.actionMainScreenFragmentToMotivationFragment()
        findNavController().navigate(action)
    }

    private fun getFriends(): Friend {
        setFriends()
        return friendGenerator.generateFriendList().random()
    }

    private fun setFriends() {
        val randomFriend = friendGenerator.generateFriendList().random()
        binding.imageView.setImageResource(randomFriend.imageResource)
        binding.txtName.text = randomFriend.name
        buttonsVisibility()
//        setFriendCharacteristics(randomFriend.characteristics)
    }

//    private fun setFriendCharacteristics(characteristics: List<String>) {
//        binding.chipGroup.removeAllViews()
//        for (characteristic in characteristics) {
//            val chip = Chip(activity)
//            chip.text = characteristic
//            binding.chipGroup.addView(chip)
//        }
//    }

    private fun showRandomImage() {
        setFriends()
        binding.btnRight.setOnClickListener {
            getFriends()
        }

        binding.btnLeft.setOnClickListener {
            getFriends()
        }
    }

    private fun buttonsVisibility() {
        when (binding.txtName.text) {
            "Georgi" -> binding.btnLeft.visibility = View.INVISIBLE
            "Stan" -> binding.btnRight.visibility = View.INVISIBLE
            else -> {
                binding.btnLeft.visibility = View.VISIBLE
                binding.btnRight.visibility = View.VISIBLE
            }
        }
    }

    private fun sendMessageToFriend() {
        binding.email.setOnClickListener {
            val user = preferencesUtil.getUserData()
            user?.let { setEmailMessage(it) }
            setEmailMessage(user)
        }
    }

    private fun setEmailMessage(user: User?) {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = getString(R.string.type_email_intent)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(user?.email))
            putExtra(Intent.EXTRA_SUBJECT, Constants.SUBJECT)
            putExtra(Intent.EXTRA_TEXT, user?.firstName + " " + user?.lastName + Constants.MESSAGE)
            putExtra(Intent.EXTRA_STREAM, Uri.fromParts("mailto", Constants.EMAIL, null))
        }
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }
}