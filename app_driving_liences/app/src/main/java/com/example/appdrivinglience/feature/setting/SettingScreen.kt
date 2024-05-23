package com.example.appdrivinglience.feature.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appdrivinglience.R
import com.example.appdrivinglience.feature.model.SettingModel

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    settingViewModel: SettingViewModel = hiltViewModel(),
    onNotificationNext: () -> Unit,
    onListNotificationNext: () -> Unit,
    onChangeTheme: (Boolean) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 30.dp),
    ) {
        item {
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = "Tài khoản",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    fontWeight = FontWeight(700),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    letterSpacing = 0.02.sp,
                )
            )
            ItemAccount(
                onNotification = onNotificationNext,
                onListNotification = onListNotificationNext,
                defaultSwitch = settingViewModel.getAppTheme()
            ) {
                onChangeTheme(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Phản hồi",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    letterSpacing = 0.02.sp,
                )
            )
            ItemFeedBack()
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Chính sách và điều khoản",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    letterSpacing = 0.02.sp,
                )
            )
            ItemPolicy()
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Phiên bản ứng dụng: v1.0.0",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                )
            }
        }
    }
}

@Composable
fun ItemAccount(
    onNotification: () -> Unit,
    onListNotification: () -> Unit,
    defaultSwitch: Boolean,
    onChangeTheme1: (Boolean) -> Unit
) {
    ItemSetting(
        settingModel = SettingModel(R.drawable.theme, "Chế độ sáng tối", isSwitch = true),
        defaultSwitch = defaultSwitch,
        onChangeTheme = onChangeTheme1
    )
    ItemSetting(
        settingModel = SettingModel(
            R.drawable.notification,
            "Thông báo"
        ), onChangeTheme = null, onNotification = onNotification
    )
    ItemSetting(
        settingModel = SettingModel(
            R.drawable.notification,
            "Danh sách thông báo"
        ), onChangeTheme = null, onNotification = onListNotification
    )
}

@Composable
fun ItemFeedBack(modifier: Modifier = Modifier) {
    ItemSetting(
        settingModel = SettingModel(R.drawable.share, "Chia sẻ ứng dụng"),
        onChangeTheme = null
    )
    ItemSetting(
        settingModel = SettingModel(R.drawable.star, "Đánh giá và nhận xét"),
        onChangeTheme = null
    )
}

@Composable
fun ItemPolicy(modifier: Modifier = Modifier) {
    ItemSetting(
        settingModel = SettingModel(R.drawable.security_safe, "Chính sách bảo mật"),
        onChangeTheme = null
    )
    ItemSetting(
        settingModel = SettingModel(R.drawable.lock, "Điều khoản sử dụng"),
        onChangeTheme = null
    )
}

@Composable
fun ItemSetting(
    modifier: Modifier = Modifier,
    settingModel: SettingModel,
    onChangeTheme: ((Boolean) -> Unit)?,
    defaultSwitch: Boolean = false,
    onNotification: (() -> Unit?)? = null
) {
    var isDarkState by remember {
        mutableStateOf(defaultSwitch)
    }
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
            .clickable {
                if (onNotification != null) {
                    onNotification()
                }
            }
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable {
                    if (onNotification != null) {
                        onNotification()
                    }
                }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = settingModel.iconRes), contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = settingModel.nameSetting,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.vietnampro_black)),
                    fontWeight = FontWeight(500),
                    color = colorResource(id = R.color.dark_ne),
                    letterSpacing = 0.02.sp,
                )
            )
            if (settingModel.isSwitch) {
                Spacer(modifier = Modifier.width(100.dp))

                Switch(
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(id = R.color.green_primary),
                        checkedTrackColor = colorResource(id = R.color.neutra_5),
                        uncheckedThumbColor = colorResource(id = R.color.neutra_4),
                        uncheckedTrackColor = colorResource(id = R.color.white),
                    ),
                    checked = isDarkState, onCheckedChange = {
                        if (onChangeTheme != null) {
                            isDarkState = !isDarkState
                            onChangeTheme(it)
                        }

                    })
            }
        }
    }
}

@Preview
@Composable
fun PreviewSetting() {
//    SettingScreen(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = colorResource(id = R.color.background))
//    )
}