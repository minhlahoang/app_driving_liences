package com.example.appdrivinglience.feature.notification_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appdrivinglience.R
import com.example.appdrivinglience.components.CommonErrorScreen
import com.example.appdrivinglience.components.CommonLoadingScreen
import com.example.appdrivinglience.components.dialog.CommonExitDialog
import com.example.appdrivinglience.core.toTimeString
import com.example.appdrivinglience.database.model.Notification

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        notificationViewModel.getAllListNotification()
    }

    var isDialogRemoveNotification by remember {
        mutableStateOf(false)
    }


    when (val state = notificationViewModel.notificationState.value) {
        is NotificationState.Error -> {
            CommonErrorScreen(error = state.error)
        }

        NotificationState.Loading -> {
            CommonLoadingScreen()
        }

        is NotificationState.Success -> {

            if (isDialogRemoveNotification){
                CommonExitDialog(title = "Bạn có muốn xóa thông báo không?",
                    onCancel = {
                        isDialogRemoveNotification = !isDialogRemoveNotification
                    }
                    , onAccept = {
                        isDialogRemoveNotification = !isDialogRemoveNotification
                        notificationViewModel.getRemoveNotification()?.let {
                            notificationViewModel.deleteNotification(it)
                        }
                    }) {
                    
                }
            }

            LazyColumn(
                modifier = modifier
                    .padding(top = 120.dp, bottom = 10.dp)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                items(state.listNotification) {
                    ItemNotification(notification = it, onLongClick = {
                        notificationViewModel.setRemoveNotification(it)
                        isDialogRemoveNotification = !isDialogRemoveNotification
                    }) {
                        notificationViewModel.cancelNotifications(it)
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemNotification(
    modifier: Modifier = Modifier,
    notification: Notification,
    onLongClick: ()-> Unit,
    onChangeStateSwitch: (Notification) -> Unit
) {
    var isSwitch by remember {
        mutableStateOf(notification.isSystemNotification)
    }
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white),
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp)
            .combinedClickable(onLongClick = {
                onLongClick()
            }) {
                println("Click Data")
            }
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${notification.hour.toTimeString()} : ${notification.minutes.toTimeString() }",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                        fontWeight = FontWeight(700),
                        color = MaterialTheme.colorScheme.secondary,
                    )
                )

                Switch(
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(id = R.color.green_primary),
                        checkedTrackColor = colorResource(id = R.color.neutra_5),
                        uncheckedThumbColor = colorResource(id = R.color.neutra_4),
                        uncheckedTrackColor = colorResource(id = R.color.white),
                    ),

                    checked = isSwitch, onCheckedChange = {
                        isSwitch = !isSwitch
                        onChangeStateSwitch(notification)
                    })
            }

            Text(
                text = if (notification.daysOfWeek == 8) "Chủ nhật" else "Thứ ${notification.daysOfWeek}",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                    fontWeight = FontWeight(500),
                    color = colorResource(id = R.color.dark_ne),
                )
            )
            Text(
                text = notification.contentNotification ?: "Unknown notification",
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(

                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                    color = MaterialTheme.colorScheme.secondary,
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewNotificationScreen() {
//    ItemNotification(modifier = Modifier.fillMaxWidth());
}