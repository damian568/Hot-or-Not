package com.example.hotornot.data

data class Friend(
    val imageResource: Int,
    val name: String,
    val email: String,
    val characteristics: List<String>,
    val isHot: Boolean
)
