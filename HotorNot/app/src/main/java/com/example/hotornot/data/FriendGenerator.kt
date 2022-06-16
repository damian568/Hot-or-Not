package com.example.hotornot.data

import android.content.Context
import com.example.hotornot.Constants
import com.example.hotornot.R

class FriendGenerator(private val context: Context) {

    fun generateFriendList(): List<Friend> {
        return listOf(
            Friend(
                imageResource = R.drawable.stan,
                name = Constants.STRING_TO_HIDE_BUTTON_HOT,
                email = "stan@gmail.com",
                characteristics = getRandomCharacteristics(),
                isHot = true
            ),
            Friend(
                imageResource = R.drawable.georgi,
                name = Constants.STRING_TO_HIDE_BUTTON_NOT,
                email = "georgi@gmail.com",
                characteristics = getRandomCharacteristics(),
                isHot = false
            ),
            Friend(
                imageResource = R.drawable.angel,
                name = "Angel",
                email = "angel@gmail.com",
                characteristics = getRandomCharacteristics(),
                isHot = false
            ),
            Friend(
                imageResource = R.drawable.mariq,
                name = "Mariq",
                email = "mariq@gmail.com",
                characteristics = getRandomCharacteristics(),
                isHot = false
            ),
            Friend(
                imageResource = R.drawable.tito,
                name = "Tito",
                email = "tito@gmail.com",
                characteristics = getRandomCharacteristics(),
                isHot = false
            ),
            Friend(
                imageResource = R.drawable.teo,
                name = "Teo",
                email = "teo@gmail.com",
                characteristics = getRandomCharacteristics(),
                isHot = false
            ),
            Friend(
                imageResource = R.drawable.krasi,
                name = "Krasi",
                email = "krasi@gmail.com",
                characteristics = getRandomCharacteristics(),
                isHot = false
            ),
            Friend(
                imageResource = R.drawable.pesho,
                name = "Pesho",
                email = "pesho@gmail.com",
                characteristics = getRandomCharacteristics(),
                isHot = false
            ),
            Friend(
                imageResource = R.drawable.aleks,
                name = "Aleks",
                email = "aleks@gmail.com",
                characteristics = getRandomCharacteristics(),
                isHot = false
            ),
            Friend(
                imageResource = R.drawable.cveti,
                name = "Cveti",
                email = "cveti@gmail.com",
                characteristics = getRandomCharacteristics(),
                isHot = false
            ),
            Friend(
                imageResource = R.drawable.miro,
                name = "Miro",
                email = "miro@gmail.com",
                characteristics = getRandomCharacteristics(),
                isHot = false
            )
        )
    }

    private fun getRandomCharacteristics(): List<String> {
        val randomCharacteristics = context.resources.getStringArray(R.array.characteristics_array)
        val randomNumberOfCharacteristic: Int =
            (Constants.MIN_NUMBER_CHARACTERISTICS..randomCharacteristics.size).random()
        return randomCharacteristics.toList().shuffled().take(randomNumberOfCharacteristic)
    }
}