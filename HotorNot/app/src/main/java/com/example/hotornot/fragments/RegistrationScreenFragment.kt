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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesUtil = PreferencesUtil.getInstance(view.context)
        setLogoImage()
        onFirstNameTextChangeListener()
        onLastNameTextChangeListener()
        onEmailTextChangeListener()
        showInterests()
        registerUser()
    }

    private fun setLogoImage() {
        binding.imgReg.setImageResource(R.drawable.ic_friends_logo)
    }

    private fun onFirstNameTextChangeListener() {
        binding.txtEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.txtFirstName.error = checkIsItEmptyFirstName()
                binding.btnReg.isEnabled = true
            }
        })
    }

    private fun onLastNameTextChangeListener() {
        binding.lastName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.txtLastName.error = checkIsItEmptyLastName()
                binding.btnReg.isEnabled = true
            }
        })
    }

    private fun onEmailTextChangeListener() {
        binding.txtEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.txtEmailReg.error = checkIsItValidEmail()
                binding.btnReg.isEnabled = true
            }
        })
    }

    private fun checkIsItEmptyFirstName(): String? {
        val firstName = binding.firstName.text.toString()
        if (TextUtils.isEmpty(firstName)) {
            return R.string.return_text.toString()
        }
        return null
    }

    private fun checkIsItEmptyLastName(): String? {
        val firstName = binding.lastName.text.toString()
        if (TextUtils.isEmpty(firstName)) {
            return R.string.return_text.toString()
        }
        return null
    }

    private fun checkIsItValidEmail(): String? {
        val emailText = binding.txtEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return R.string.return_email_text.toString()
        }
        return null
    }

    private fun reloadingAdapter() {
        val arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.interests))
        binding.spinnerMenu.adapter = arrayAdapter
    }

    private fun showInterests() {
        reloadingAdapter()
        binding.spinnerMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                showItemSelectedToast(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                showErrorToastInInterests()
            }
        }
    }

    private fun showItemSelectedToast(position: Int) {
        Toast.makeText(requireContext(),
            R.string.select_toast.toString() + resources.getStringArray(R.array.interests)[position],
            Toast.LENGTH_LONG).show()
    }

    private fun showErrorToastInInterests() {
        Toast.makeText(requireContext(),
            R.string.error_toast.toString(),
            Toast.LENGTH_LONG).show()
    }

    private fun createUser() {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val email = binding.txtEmail.text.toString()

        val user = User(
            firstName,
            lastName,
            email,
            getSelectRadioBtnValue(), getSelectInterest()
        )
        preferencesUtil.saveUserData(user)
    }

    private fun getSelectRadioBtnValue() =
        when (binding.radioGroupGender.checkedRadioButtonId) {
            R.id.btnRadioMan -> Gender.Male
            R.id.btnRadioWoman -> Gender.Female
            R.id.btnRadioOther -> Gender.Other
            else -> Gender.Other
        }

    private fun registerUser() {
        binding.btnReg.setOnClickListener {
            checkIsItEmpty()
        }
    }

    private fun getSelectInterest(): String {
        return " "
    }

    private fun checkIsItEmpty() {
        val firstName = binding.txtFirstName.editText?.text.toString()
        val lastName = binding.txtLastName.editText?.text.toString()
        val email = binding.txtEmailReg.editText?.text.toString()

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email)
        ) {
            showErrorToastVerification()
        } else {
            createUser()
            goToMainScreenFragment()
        }
    }

    private fun showErrorToastVerification() {
        Toast.makeText(this.context, R.string.field_toast.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun goToMainScreenFragment() {
        val action =
            RegistrationScreenFragmentDirections.actionRegistrationScreenFragmentToMainScreenFragment()
        findNavController().navigate(action)
    }
}