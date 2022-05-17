package com.example.hotornot.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.hotornot.*
import com.example.hotornot.data.User
import com.example.hotornot.enums.Gender
import com.example.hotornot.databinding.FragmentRegistrationScreenBinding

class RegistrationScreenFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationScreenBinding
    private lateinit var preferencesUtil: PreferencesUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegistrationScreenBinding.inflate(layoutInflater, container, false)
        binding.imgReg.setImageResource(R.drawable.ic_friends_logo)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesUtil = PreferencesUtil.getInstance(view.context)
        onFirstNameTextChangeListener()
        onLastNameTextChangeListener()
        onEmailTextChangeListener()
        showInterests()
        isRegisterUser()
    }

    private fun onFirstNameTextChangeListener() {
        binding.txtEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.txtFirstName.error = isEmptyFirstName()
                binding.btnReg.isEnabled = true
            }
        })
    }

    private fun onLastNameTextChangeListener() {
        binding.lastName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.txtLastName.error = isEmptyLastName()
                binding.btnReg.isEnabled = true
            }
        })
    }

    private fun onEmailTextChangeListener() {
        binding.txtEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.txtEmailReg.error = isValidEmail()
                binding.btnReg.isEnabled = true
            }
        })
    }

    private fun isEmptyFirstName(): String? {
        val firstName = binding.firstName.text.toString()
        if (TextUtils.isEmpty(firstName)) {
            return "Can't be empty!"
        }
        return null
    }

    private fun isEmptyLastName(): String? {
        val firstName = binding.lastName.text.toString()
        if (TextUtils.isEmpty(firstName)) {
            return "Can't be empty!"
        }
        return null
    }

    private fun isValidEmail(): String? {
        val emailText = binding.txtEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address!"
        }
        return null
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

    private fun createUser() {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val email = binding.txtEmail.text.toString()

        val user = User(
            firstName,
            lastName,
            email,
            getSelectRadioBtnValue(), ""
        )
        preferencesUtil.saveUserData(user)
    }

    private fun getSelectRadioBtnValue() =
        when (binding.rgGender.checkedRadioButtonId) {
            R.id.btnRadioMan -> Gender.Male
            R.id.btnRadioWoman -> Gender.Female
            R.id.btnRadioOther -> Gender.Other
            else -> Gender.Other
        }

    private fun isRegisterUser() {
        binding.btnReg.setOnClickListener {
            isItEmpty()
        }
    }

    private fun isItEmpty() {
        val firstName = binding.txtFirstName.editText?.text.toString()
        val lastName = binding.txtLastName.editText?.text.toString()
        val email = binding.txtEmailReg.editText?.text.toString()

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(
                email)
        ) {
            Toast.makeText(this.context, "Field is required!", Toast.LENGTH_SHORT).show()
        } else {
            createUser()
            goToNextScreen()
        }
    }

    private fun goToNextScreen() {
        val action =
            RegistrationScreenFragmentDirections.actionRegistrationScreenFragmentToMainScreenFragment()
        findNavController().navigate(action)
    }
}