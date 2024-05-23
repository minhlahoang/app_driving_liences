package com.example.appdrivinglience.components.dialog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdrivinglience.R
import com.example.appdrivinglience.components.button.CommonButton

@Composable
fun ResultDialogScreen(
    modifier: Modifier = Modifier,
    percentage: Int,
    onAction :()-> Unit,
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .width(296.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "BẠN CẦN NỖ LỰC HƠN NỮA!",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontFamily = FontFamily(Font(R.font.vietnampro_black)),
                fontWeight = FontWeight(700),
                color = colorResource(id = R.color.red),

                )
        )
        Text(
            text = "Tỉ lệ đỗ của bạn: $percentage%",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontFamily = FontFamily(Font(R.font.vietnampro_black)),
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.dark_ne),
            )
        )
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.circle_vector),
                contentDescription = null
            )
            Image(
                painter = painterResource(id = R.drawable.img_person_effort_vector),
                contentDescription = null
            )
        }
        Text(
            text = "Bạn sẽ đạt 100% tỉ lệ đỗ khi vượt qua hết tất cả các đề thi (điểm ĐẠT) trong phần thi sát hạch của hạng bằng lái tương ứng!",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = FontFamily(Font(R.font.vietnampro_black)),
                fontWeight = FontWeight(400),
                color = colorResource(id = R.color.dark_ne),
            ),
            textAlign = TextAlign.Justify
        )
        
        CommonButton(title = "Đã hiểu", onAction = {
            onAction()
        })
        
    }
}

@Preview
@Composable
fun PreviewResultDialog() {
//    ResultDialogScreen(
//        modifier = Modifier.fillMaxSize()
//    );
}