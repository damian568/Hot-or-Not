package com.example.hotornot.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        showRandomPerson()
        sendMessageToFriend()
    }

    private fun getFriends(): Friend {
        setFriends()
        val friend = preferencesUtil.getFriends().random()
        binding.imageView.setImageResource(friend.imageResource)
        binding.txtName.text = friend.name
        setFriendCharacteristics(friend.characteristics)
        return friend
    }

    private fun setFriends() {
        preferencesUtil.setFriends(friendGenerator.generateFriendList())
        buttonsVisibility()
    }

    private fun setFriendCharacteristics(characteristics: List<String>) {
        binding.chipGroup.removeAllViews()
        for (characteristic in characteristics) {
            val chip = Chip(view?.context)
            chip.text = characteristic
            binding.chipGroup.addView(chip)
        }
    }

    private fun showRandomPerson() {
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
            Constants.STRING_TO_HIDE_BUTTON_NOT -> binding.btnLeft.visibility = View.INVISIBLE
            Constants.STRING_TO_HIDE_BUTTON_HOT -> binding.btnRight.visibility = View.INVISIBLE
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