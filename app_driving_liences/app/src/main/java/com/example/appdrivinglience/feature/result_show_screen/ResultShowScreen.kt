package com.example.appdrivinglience.feature.result_show_screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appdrivinglience.R
import com.example.appdrivinglience.feature.examination_test_screen.TestExaminationViewModel

@Composable
fun ResultShowScreen(
    viewModel: TestExaminationViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onAccept: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .padding(top = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        val score = if (viewModel.getCategoryLicense() == "A1" ||
            viewModel.getCategoryLicense() == "A2"||
                viewModel.getCategoryLicense() == "A3"||
                viewModel.getCategoryLicense() == "A4") 22 else{
                32
        }
        val buildText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                color =
                if (viewModel.scoreState.value > score) colorResource(id = R.color.green_primary)
                else colorResource(id = R.color.red)
            )) {
                append("${viewModel.scoreState.value}/")
            }

            withStyle(style = SpanStyle(fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                color = colorResource(id = R.color.black)
            )) {
                append("${viewModel.listQuestions.size}")
            }
        }
        if (viewModel.scoreState.value > score && viewModel.isPassState.value) {
            Text(
                text = "Thật tuyệt vời\nbài thi thật là xuất sắc",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.dark_ne),
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(id = R.drawable.img_congrulation),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = buildText,
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    fontWeight = FontWeight(700),
                    color = colorResource(id = R.color.green_primary),

                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "ĐẠT",
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    fontWeight = FontWeight(700),
                    color = colorResource(id = R.color.green_primary),
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Bạn thật tuyệt vời, hãy giữ vững phong độ ở những lần thi tiếp theo nha!",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.dark_ne),
                )
            )
        }else {
            Text(
                text = "Ôi trượt rồi\n" + "bạn hãy cố gắng hơn nữa nha!",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.dark_ne),
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(id = R.drawable.img_congrulation),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = buildText,
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    fontWeight = FontWeight(700),
                    color = colorResource(id = R.color.red),

                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "KHÔNG ĐẠT",
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    fontWeight = FontWeight(700),
                    color = colorResource(id = R.color.red),
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Thắng không kiêu, bại không nản có chí thì nên, hãy tiếp tục cố gắng ở những lần thi tiếp theo nhé",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.dark_ne),
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(150.dp)
                    .height(40.dp)
                    .background(
                        color = colorResource(id = R.color.neutra_2),
                        shape = RoundedCornerShape(size = 10.dp)
                    )

                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onCancel()
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.clickable {
                            onCancel()
                        },
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {
                                onCancel()
                            },
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
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(150.dp)
                    .height(40.dp)

                    .background(
                        color = colorResource(id = R.color.green_primary),
                        shape = RoundedCornerShape(size = 10.dp)
                    )

                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onAccept()
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.clickable {
                            onAccept()
                        },
                        painter = painterResource(id = R.drawable.activity),
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {
                                viewModel.saveExaminationLicense(viewModel.numberExamination,viewModel.scoreState.value)
                                viewModel.saveUserAnswerExamination(viewModel.numberExamination)
                                viewModel.saveIsWrongImportant(viewModel.numberExamination)
                                viewModel.saveIsPass(viewModel.numberExamination,(viewModel.scoreState.value > score && viewModel.isPassState.value))
                                onAccept()
                            },
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
