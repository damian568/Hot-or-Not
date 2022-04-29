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
import com.example.hotornot.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)
        slowedFragment()
        onClickButton()
        buttonVisibility()
        return binding.root
    }

    private fun slowedFragment(){
        Handler(Looper.getMainLooper()).postDelayed({
            goToMainScreen()
        }, delayMills.toLong())
    }

    private fun goToMainScreen() {
        val action = MainScreenFragmentDirections.actionMainScreenFragmentToRegistrationScreenFragment()
        findNavController().navigate(action)
    }
    
    private fun onClickButton(){
        binding.btnRight.setOnClickListener  {
            binding.imageView.setImageResource(R.drawable.stan)
            binding.txtName.text = R.string.stan.toString()
        }

        binding.btnLeft.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.georgi)
            binding.txtName.text = R.string.georgi.toString()

        }
    }

    private fun buttonVisibility(){
        if(binding.txtName.text == "Georgi")binding.btnLeft.visibility
        else if (binding.txtName.text == "Stan")binding.btnRight.visibility
    }
}