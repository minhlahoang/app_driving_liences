package com.example.appdrivinglience.components

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.appdrivinglience.R

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: Int,
) {
    data object OnBoardingScreen : Destinations(
        route = "on_boarding_screen",
        title = "On Boarding",
        icon = R.drawable.home_2
    )
    data object HomeScreen : Destinations(
        route = "home_screen",
        title = "Trang chủ",
        icon = R.drawable.home_2
    )

    data object TheoryScreen : Destinations(
        route = "theory_screen",
        title = "Lý thuyết",
        icon = R.drawable.book_home

    )

    data object TestMockScreen : Destinations(
        route = "test_screen",
        title = "Thi thử",
        icon = R.drawable.test_mock_home
    )

    data object SignScreen : Destinations(
        route = "sign_screen",
        title = "Biển báo",
        icon = R.drawable.minus_cirlce
    )

    data object SettingScreen : Destinations(
        route = "setting_screen",
        title = "Cài đặt",
        icon = R.drawable.setting_2

    )

}

sealed class OnBoardingDestination(
    val route: String,
    val title: String? = null,
    val icon: Int,
) {
    data object OnBoardingScreen : Destinations(
        route = "on_boarding_screen",
        title = "On Boarding",
        icon = R.drawable.home_2
    )
    data object HomeScreen : Destinations(
        route = "home_screen",
        title = "Trang chủ",
        icon = R.drawable.home_2
    )

    data object TheoryScreen : Destinations(
        route = "theory_screen",
        title = "Lý thuyết",
        icon = R.drawable.book_home

    )

    data object TestMockScreen : Destinations(
        route = "test_screen",
        title = "Thi thử",
        icon = R.drawable.test_mock_home
    )

    data object SignScreen : Destinations(
        route = "sign_screen",
        title = "Biển báo",
        icon = R.drawable.minus_cirlce
    )

    data object SettingScreen : Destinations(
        route = "setting_screen",
        title = "Cài đặt",
        icon = R.drawable.setting_2

    )

}