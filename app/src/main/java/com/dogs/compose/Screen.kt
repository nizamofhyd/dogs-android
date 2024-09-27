package com.dogs.compose

sealed class Screen(val route: String) {
    data object HOME: Screen("home")
    data object BREED_DETAILS: Screen("breed-details")
}