package com.example.hotornot.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hotornot.databinding.FragmentSplashScreenBinding

const val delayMills = 1000

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        slowedFragment()
        return binding.root
    }

    private fun slowedFragment(){
        Handler(Looper.getMainLooper()).postDelayed({
            navigateFragment()
        }, delayMills.toLong())
    }

    private fun navigateFragment() {
        val action = SplashScreenFragmentDirections.actionSpashScreenFragmentToMainScreenFragment()
        findNavController().navigate(action)
    }
}