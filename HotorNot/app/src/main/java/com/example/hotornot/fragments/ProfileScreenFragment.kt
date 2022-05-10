package com.example.hotornot.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.hotornot.PreferencesUtil
import com.example.hotornot.R
import com.example.hotornot.UserSharedPreference
import com.example.hotornot.databinding.FragmentProfileScreenBinding

class ProfileScreenFragment : Fragment(R.layout.fragment_profile_screen) {

    private lateinit var binding: FragmentProfileScreenBinding
    private val preference = context?.let { UserSharedPreference(it) }
    private val preferencesUtil: PreferencesUtil? = context?.let { PreferencesUtil.getInstance(it) }

    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileScreenBinding.inflate(layoutInflater, container, false)
        changeImage()
        printUserInfo()
        return binding.root
    }

    private fun printUserInfo(){
        binding.txtFullNameProfile.text = preferencesUtil?.getUserData()?.firstName
        binding.txtFullNameProfile.text = preferencesUtil?.getUserData()?.lastName
        binding.txtEmailProfile.text = preferencesUtil?.getUserData()?.email
    }

    private fun changeImage() {
        binding.imageViewProfile.setOnClickListener {
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            binding.imageViewProfile.setImageURI(data?.data)
        }
    }
}