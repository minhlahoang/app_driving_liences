package com.example.appdrivinglience.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.appdrivinglience.R
import com.example.appdrivinglience.database.model.SignModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignDialog(modifier: Modifier = Modifier, signModel: SignModel, onDismiss : ()-> Unit) {

    ModalBottomSheet(
        onDismissRequest = {
        onDismiss()
    }) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                painter = rememberAsyncImagePainter(signModel.imgUrlSign),
                contentDescription = null
            )
            Text(text = signModel.nameSign, style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                color = MaterialTheme.colorScheme.secondary,
            ))
            Text(text = signModel.description, style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                color = MaterialTheme.colorScheme.secondary,
            ))
        }
    }

}

@Preview
@Composable
fun PreviewSign(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
//        SignDialog()
    }
}