package com.example.appdrivinglience.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdrivinglience.R

@Composable
fun CommonButton(title: String, modifier: Modifier= Modifier, onAction :(() -> Unit)?= null){
    Box(
        modifier = modifier
            .width(296.dp)
            .height(40.dp)
            .clickable {
                if (onAction != null) {
                    onAction()
                }
            }
            .background(
                color = colorResource(id = R.color.green_primary),
                shape = RoundedCornerShape(size = 10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.vietnampro_black)),
                fontWeight = FontWeight(600),
                color = colorResource(id = R.color.white),
            )
        )
    }
}