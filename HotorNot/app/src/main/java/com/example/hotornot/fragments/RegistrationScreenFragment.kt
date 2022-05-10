package com.example.hotornot.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.hotornot.databinding.FragmentRegistrationScreenBinding

class RegistrationScreenFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationScreenBinding
    private val preference = context?.let { UserSharedPreference(it) }
    private val preferencesUtil: PreferencesUtil? = context?.let { PreferencesUtil.getInstance(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegistrationScreenBinding.inflate(layoutInflater, container, false)
        binding.imgReg.setImageResource(R.drawable.ic_friends_logo)
        binding.progressBar.visibility = View.INVISIBLE
        onFirstNameTextChangeListener()
        onLastNameTextChangeListener()
        onEmailTextChangeListener()
        showInterests()
        goToProfileScreen()
        return binding.root
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

    private fun isValidString(str: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
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

    private fun goToProfileScreen() {
        binding.btnReg.setOnClickListener {
            val firstName = binding.txtFirstName.editText?.text.toString()
            val lastName = binding.txtLastName.editText?.text.toString()
            val email = binding.txtEmailReg.editText?.text.toString()
            val user = User(firstName, lastName, email)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.progressBar.visibility = View.VISIBLE

                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
                }
                if (isValidString(email)) {
                    preferencesUtil?.saveUserData(user)
//                    preference?.saveUserDates(firstName, lastName, email)
                    val action =
                        RegistrationScreenFragmentDirections.actionRegistrationScreenFragmentToMainScreenFragment()
                    findNavController().navigate(action)
                }
            }, delayMills.toLong())
            binding.progressBar.visibility = View.GONE
        }

        fun saveUserData() {
            preference?.putString(Constants.keyFirstName, binding.firstName.text.toString())
            preference?.putString(Constants.keyLastName, binding.lastName.text.toString())
            preference?.putString(Constants.keyEmail, binding.txtEmail.text.toString())
            when (binding.rgGender.checkedRadioButtonId) {
                R.id.btnRadioMan -> {
                    preference?.putBoolean(Constants.keyGender,
                        binding.rgGender.checkedRadioButtonId == R.id.btnRadioMan)
                }
                R.id.btnRadioWoman -> {
                    preference?.putBoolean(Constants.keyGender,
                        binding.rgGender.checkedRadioButtonId == R.id.btnRadioWoman)
                }
                R.id.btnRadioOther -> {
                    preference?.putBoolean(Constants.keyGender,
                        binding.rgGender.checkedRadioButtonId == R.id.btnRadioOther)
                }
            }
        }
    }
}