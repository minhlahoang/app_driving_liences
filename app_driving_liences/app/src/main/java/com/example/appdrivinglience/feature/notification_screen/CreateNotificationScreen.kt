package com.example.appdrivinglience.feature.notification_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appdrivinglience.R
import com.example.appdrivinglience.components.button.CommonButton
import com.example.appdrivinglience.core.toTimeString
import com.example.appdrivinglience.database.model.Notification
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNotificationScreen(
    modifier: Modifier = Modifier,
    notificationViewModel: NotificationViewModel = hiltViewModel(),
    onNextScreen: () -> Unit
) {
    var contentNotification by remember {
        mutableStateOf("")
    }
    var selectedDays by remember {
        mutableIntStateOf(-1)
    }
    var isShowTimePicker by remember {
        mutableStateOf(false)
    }
    var hours by remember {
        mutableIntStateOf(0)
    }
    var minutes by remember {
        mutableIntStateOf(0)
    }
    val timePickerState = rememberTimePickerState(
        is24Hour = true
    )

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {

        if (isShowTimePicker){
            TimePickerDialog(
                onCancel = { isShowTimePicker = !isShowTimePicker},
                onConfirm = {
                    hours = timePickerState.hour
                    minutes = timePickerState.minute
                    isShowTimePicker = !isShowTimePicker
                }) {
                TimePicker(
                    state = timePickerState
                )
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Thời gian",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                )
            )
            Text(
                modifier = Modifier.clickable {
                    isShowTimePicker = true
                },
                text = "${hours.toTimeString()}:${minutes.toTimeString()}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Ngày",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                fontWeight = FontWeight(700),
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
            )
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.Start
        ) {
            items(7) {
                ItemDay(
                    index = it,
                    isSelected = selectedDays == it,
                ) {
                    selectedDays = it
                }
            }
        }
        Text(
            text = "Lời nhắc nhở",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                fontWeight = FontWeight(700),
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .shadow(
                    elevation = 8.dp,
                    spotColor = colorResource(id = R.color.gradient_white),
                    ambientColor = colorResource(id = R.color.gradient_white)
                )
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(size = 10.dp)
                ),
            maxLines = 3,
            value = contentNotification, onValueChange = {
                contentNotification = it
            })

        CommonButton(
            title = "TẠO NHẮC NHỞ", modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
        ) {
            val notification = Notification(
                hour = hours,
                minutes = minutes,
                daysOfWeek = selectedDays + 2,
                contentNotification = contentNotification,
                time = Calendar.getInstance().timeInMillis
            )
            notificationViewModel.insertNotification(notification)
            notificationViewModel.scheduleLeastNotification(notification)
            onNextScreen()
        }
    }
}

@Composable
fun ItemDay(
    modifier: Modifier = Modifier,
    index: Int,
    isSelected: Boolean = false,
    onSelected: (Int) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = {
                onSelected(index)
            }
        )
        if (index + 2 == 8) {
            Text(text = "Chủ nhật")
        } else {
            Text(text = "Thứ ${index + 2}")
        }
    }
}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}



@Preview
@Composable
fun PreviewCreateNotification() {
//    CreateNotificationScreen(
//        modifier = Modifier.fillMaxSize()
//    );
}