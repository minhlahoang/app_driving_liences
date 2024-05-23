package com.example.appdrivinglience.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.appdrivinglience.R

@Composable
fun CommonDialog(
    modifier: Modifier = Modifier,
    title: String = "",
    nameButton: String = "",
    onClick: ()-> Unit,
    onDismiss: ()-> Unit,
    container: @Composable () -> Unit
) {

    Dialog(onDismissRequest = {
        onDismiss()
    },

        ) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.white),
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily(Font(R.font.vietnampro_black)),
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.dark_ne),
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Divider(
                    modifier = Modifier
                        .height(1.dp)
                        .width(296.dp)
                        .background(color = colorResource(id = R.color.neutra_3))
                )
                Spacer(modifier = Modifier.height(16.dp))

                container()
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable {
                            onClick()
                        }
                        .background(
                            color = colorResource(id = R.color.green_primary),
                        )
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = nameButton,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                            fontWeight = FontWeight(600),
                            color = colorResource(id = R.color.white),
                        )
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewCommonDialog() {
    CommonDialog(
        title = "Tạo đề thi ngẫu nhiên",
        nameButton = "Tạo đề thi",
        onClick = {},
        onDismiss = {},
        modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Đề thi ngẫu nhiên được tạo nên từ các câu hỏi trong ngân hàng câu hỏi. Đề thi sẽ có cấu trúc giống đề thi thật. Chúc bạn thi tốt!",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                fontWeight = FontWeight(400),
                color = colorResource(id = R.color.dark_ne),
                textAlign = TextAlign.Center,
            )
        )
    }
}