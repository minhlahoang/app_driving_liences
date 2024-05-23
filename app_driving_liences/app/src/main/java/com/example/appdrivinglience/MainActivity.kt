package com.example.appdrivinglience

import RootNavGraph
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.appdrivinglience.core.ReadDataManager
import com.example.appdrivinglience.core.ReadFileUtils
import com.example.appdrivinglience.core.SettingSharePreference
import com.example.appdrivinglience.database.model.SignModel
import com.example.appdrivinglience.ui.theme.AppDrivingLienceTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalPermissionsApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var  readFileUtils: ReadFileUtils
    @Inject
    lateinit var sharePreference: SettingSharePreference
    @Inject
    lateinit var readDataManager: ReadDataManager

    companion object {
        val appBanSignList = mutableListOf<SignModel>()
        val appDangerousSignList = mutableListOf<SignModel>()
        val appTempSignList = mutableListOf<SignModel>()
        val appGuideSignList = mutableListOf<SignModel>()
        val appControlSignList = mutableListOf<SignModel>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!readDataManager.getInstalledApps()){
            readFileUtils.readTrickFile(this)
            readFileUtils.readNVVTTheoryFile(this)
            readFileUtils.readCTSCTheoryFile(this)
            readFileUtils.readVHTheoryFile(this)
            readFileUtils.readKNQTFile(this)
            readFileUtils.readDLFile(this)
            readFileUtils.readSHFile(this)
            readFileUtils.readBBFile(this)


        }
        appBanSignList.addAll(readFileUtils.readBanSignFile(this))
        appDangerousSignList.addAll(readFileUtils.readDangerousSignFile(this))
        appTempSignList.addAll(readFileUtils.readTempSignFile(this))
        appGuideSignList.addAll(readFileUtils.readGuideSignFile(this))
        appControlSignList.addAll(readFileUtils.readControlSignFile(this))
        val mainViewModel by viewModels<MainViewModel>()
        val systemTheme = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> { true }
            Configuration.UI_MODE_NIGHT_NO -> { false }
            else -> { sharePreference.getAppTheme() }
        }
        mainViewModel.setAppTheme(systemTheme)
        setContent {

            AppDrivingLienceTheme(darkTheme = mainViewModel.appThemeLiveData.observeAsState().value ?: false) {
                enableEdgeToEdge(statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT,Color.TRANSPARENT))
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {

                    RootNavGraph(readDataManager){
                        if(mainViewModel.appThemeLiveData.value == true ) {
                            mainViewModel.setAppTheme(false)
                            sharePreference.saveAppTheme(false)
                        }else {
                            mainViewModel.setAppTheme(true)
                            sharePreference.saveAppTheme(true)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppDrivingLienceTheme {
        Greeting("Android")
    }
}