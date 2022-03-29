package com.example.hotornot.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotornot.R
import com.example.hotornot.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)

        clickButton()

        return binding.root
    }
    
    private fun clickButton(){
        binding.btnRight.setOnClickListener  {
            binding.imageView.setImageResource(R.drawable.woman)
            binding.txtName.text = "Kalina"
        }

        binding.btnLeft.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.men)
            binding.txtName.text = "Georgi"
        }
    }
}