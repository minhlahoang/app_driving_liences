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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.appdrivinglience.R

@Composable
fun CommonExitDialog(
    modifier: Modifier = Modifier,
    title: String,
    onCancel :()-> Unit,
    onAccept :()-> Unit,
    container: @Composable () -> Unit?,

) {

    Dialog(onDismissRequest = { onCancel() }) {
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                        fontWeight = FontWeight(700),
                        color = colorResource(id = R.color.dark_ne),
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                container()
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(140.dp)
                            .height(40.dp)
                            .clickable {
                                onCancel()
                            }
                            .background(
                                color = colorResource(id = R.color.neutra_2),
                                shape = RoundedCornerShape(size = 10.dp)
                            )

                            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = "Hủy bỏ",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                                fontWeight = FontWeight(600),
                                color = colorResource(id = R.color.white),
                            )
                        )
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(140.dp)
                            .height(40.dp)
                            .clickable {
                                onAccept()
                            }
                            .background(
                                color = colorResource(id = R.color.green_primary),
                                shape = RoundedCornerShape(size = 10.dp)
                            )

                            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = "Đồng ý",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
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
}
@Preview
@Composable
fun PreviewCommonExitDialog() {
    CommonExitDialog(modifier = Modifier.fillMaxWidth(), title = "", onAccept ={}, onCancel = {} ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Thời gian còn lại: 18:58",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                    fontWeight = FontWeight(400),
                    color = colorResource(id = R.color.dark_ne),
                    textAlign = TextAlign.Center,
                )
            )

            Text(
                text = "Số câu chưa làm: 30",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                    fontWeight = FontWeight(400),
                    color =colorResource(id = R.color.dark_ne),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}
