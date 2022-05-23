package com.example.hotornot.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hotornot.PreferencesUtil
import com.example.hotornot.databinding.FragmentSplashScreenBinding

const val DELAY_MILLS = 3000

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var preferencesUtil: PreferencesUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesUtil = PreferencesUtil.getInstance(view.context)
        slowedFragment()
    }

    private fun slowedFragment() {
        val user = preferencesUtil.getUserData()
        Handler(Looper.getMainLooper()).postDelayed({
            if (user == null) {
                goToRegistrationScreen()
            } else {
                goToMainScreen()
            }
        }, DELAY_MILLS.toLong())
    }

    private fun goToMainScreen() {
        val action = SplashScreenFragmentDirections.actionSpashScreenFragmentToMainScreenFragment()
        findNavController().navigate(action)
    }

    private fun goToRegistrationScreen() {
        val action =
            SplashScreenFragmentDirections.actionSpashScreenFragmentToRegistrationScreenFragment()
        findNavController().navigate(action)
    }
}