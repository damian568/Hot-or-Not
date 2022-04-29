package com.example.hotornot.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.hotornot.R
import com.example.hotornot.databinding.FragmentRegistrationScreenBinding

class RegistrationScreenFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegistrationScreenBinding.inflate(layoutInflater, container, false)
        binding.imgReg.setImageResource(R.drawable.ic_friends_logo)
        firstNameFocusListener()
        lastNameFocusListener()
        emailFocusListener()
        showInterests()
        goToProfileScreen()
        return binding.root
    }

    private fun firstNameFocusListener() {
        binding.firstName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.txtFirstName.error = isEmptyFirstName()
            }
        }
    }

    private fun isEmptyFirstName(): String? {
        val firstName = binding.firstName.text.toString()
        if (TextUtils.isEmpty(firstName)) {
            return "Can't be empty!"
        }
        return null
    }

    private fun lastNameFocusListener() {
        binding.lastName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.txtLastName.error = isEmptyLastName()
            }
        }
    }

    private fun isEmptyLastName(): String? {
        val firstName = binding.lastName.text.toString()
        if (TextUtils.isEmpty(firstName)) {
            return "Can't be empty!"
        }
        return null
    }

    private fun emailFocusListener() {
        binding.txtEmail.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.txtEmailReg.error = isValidEmail()
            }
        }
    }

    private fun isValidEmail(): String? {
        val emailText = binding.txtEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address!"
        }
        return null
    }

    private fun isValidString(str: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

    private fun goToProfileScreen() {
        binding.btnReg.setOnClickListener {
            val email: String = binding.txtEmail.text.toString()
            val firstName: String = binding.firstName.text.toString()
            val lastName: String = binding.lastName.text.toString()

            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
                binding.btnReg.isEnabled = true
            } else if (isValidString(email)){
                val action =
                    RegistrationScreenFragmentDirections.actionRegistrationScreenFragmentToProfileScreenFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun showInterests() {
        val arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.interests))
        binding.spinnerMenu.adapter = arrayAdapter
        binding.spinnerMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                Toast.makeText(requireContext(),
                    "You select " + resources.getStringArray(R.array.interests)[position],
                    Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireContext(),
                    "Please select one of our options!",
                    Toast.LENGTH_LONG).show()
            }

        }
    }
}