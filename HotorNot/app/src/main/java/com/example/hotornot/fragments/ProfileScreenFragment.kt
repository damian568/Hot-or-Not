package com.example.hotornot.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.hotornot.Constants
import com.example.hotornot.PreferencesUtil
import com.example.hotornot.data.User
import com.example.hotornot.databinding.FragmentProfileScreenBinding

class ProfileScreenFragment : Fragment() {

    private lateinit var binding: FragmentProfileScreenBinding
    private lateinit var preferencesUtil: PreferencesUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesUtil = PreferencesUtil.getInstance(view.context)
        val user = preferencesUtil.getUserData()
        user?.let { printUserInfo(it) }
        changeImage()
    }

    private fun printUserInfo(user: User?) {
        binding.txtFullNameProfile.text = user?.firstName + " " + user?.lastName
        binding.txtEmailProfile.text = user?.email
        binding.txtSexProfile.text = user?.gender.toString()
    }

    private fun changeImage() {
        binding.imageViewProfile.setOnClickListener {
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = Constants.IMAGE_TEXT
        openGalleryInNewActivity(intent)
    }

    private fun openGalleryInNewActivity(intent: Intent) {
        var resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    binding.imageViewProfile.setImageURI(data?.data)
                }
            }
        resultLauncher.launch(intent)
    }
}