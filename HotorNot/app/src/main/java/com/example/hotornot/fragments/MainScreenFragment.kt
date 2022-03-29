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
        binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)
        onClickButton()
        return binding.root
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
}