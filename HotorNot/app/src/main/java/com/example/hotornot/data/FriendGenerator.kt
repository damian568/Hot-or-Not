package com.example.hotornot.data

import android.content.Context
import android.widget.Button
import android.widget.TextView
import com.example.hotornot.PreferencesUtil
import com.example.hotornot.R
import org.w3c.dom.Text
import java.util.jar.Attributes

private const val STRING_TO_HIDE_BUTTON_HOT = "Stan"
private const val STRING_TO_HIDE_BUTTON_NOT = "Georgi"
private const val MIN_NUMBER_CHARACTERISTICS = 3

class FriendGenerator(private val context: Context) {

    fun generateFriendList(): List<Friend> {
        return listOf(
            Friend(
                imageResource = R.drawable.stan,
                name = STRING_TO_HIDE_BUTTON_HOT,
                email = "stan@gmail.com",
                characteristics = getRandomCharacteristics(),
                isHot = true
            ),
            Friend(
                imageResource = R.drawable.georgi,
                name = STRING_TO_HIDE_BUTTON_NOT,
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
            (MIN_NUMBER_CHARACTERISTICS..randomCharacteristics.size).random()
        return randomCharacteristics.toList().shuffled().take(randomNumberOfCharacteristic)
    }
}