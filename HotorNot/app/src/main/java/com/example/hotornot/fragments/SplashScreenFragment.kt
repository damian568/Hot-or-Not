package com.example.hotornot.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hotornot.R
import com.example.hotornot.databinding.FragmentSplashScreenBinding

const val delayMills = 2000

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        delayFragment()
        return binding.root
    }

    private fun delayFragment(){
        Handler(Looper.getMainLooper()).postDelayed({
            replaceFragment()
        }, delayMills.toLong())
    }

    private fun replaceFragment() {
        val action = SplashScreenFragmentDirections.actionSpashScreenFragmentToMainScreenFragment()
        findNavController().navigate(action)
    }
}