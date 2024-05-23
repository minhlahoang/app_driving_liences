package com.example.appdrivinglience.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.appdrivinglience.R

@Composable
fun CommonErrorScreen(
    modifier: Modifier = Modifier,
    error: String?
){
    Column(
        modifier = modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = error ?: "Unknown Error",
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 20.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
            )
        )
    }

}

@Composable
fun CommonLoadingScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}