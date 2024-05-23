package com.example.appdrivinglience.feature.on_boarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdrivinglience.R

@Composable
fun CommonOnBoardingScreen(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    @DrawableRes idRes: Int,
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title, style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.bevietnampro_bold, FontWeight(700))),
                color = MaterialTheme.colorScheme.secondary,

                )
        )

        Image(painter = painterResource(id = idRes), contentDescription = null)
        Text(
            modifier = Modifier.padding(16.dp),
            text = content, style = TextStyle(
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.bevietnampro_bold, FontWeight(500))),
                color = MaterialTheme.colorScheme.secondary,
                
                )
        )
    }

}


@Preview
@Composable
fun PreviewCommon() {
    Column(modifier = Modifier.fillMaxSize()) {
       CommonOnBoardingScreen(
           title = "Ngày thứ 1",
           content =
           "Dành 8 tiếng học hết các loại biển báo hay gặp. Tập trung vào biển báo cấm, hiệu lệnh, chỉ dẫn, nguy hiểm...",            idRes = R.drawable.day_1
       )
   }
}