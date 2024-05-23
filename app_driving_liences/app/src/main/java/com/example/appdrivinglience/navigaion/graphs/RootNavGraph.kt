import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appdrivinglience.core.ReadDataManager
import com.example.appdrivinglience.feature.home_screen.HomeViewModel
import com.example.appdrivinglience.navigaion.Graph
import com.example.appdrivinglience.navigaion.graphs.MainScreen
import com.example.appdrivinglience.navigaion.graphs.onBoardingNavGraph

@SuppressLint("SuspiciousIndentation")
@Composable
fun RootNavGraph( readDataManager: ReadDataManager, homeViewModel: HomeViewModel = hiltViewModel(), onChangeTheme : (Boolean)-> Unit) {
    val startDestination = if (readDataManager.getInstalledApps()) Graph.MainScreenGraph else{
        Graph.OnBoardingScreen
    }
    LaunchedEffect(key1 = Unit) {
        if (!readDataManager.getInstalledApps()){
            homeViewModel.insertAllCategoryLicense()
            homeViewModel.insertCategoryQuestion()
            readDataManager.setInstalledApps(true)

        }
    }
    val onBoardingController = rememberNavController()
       NavHost(navController = onBoardingController,
           startDestination = startDestination, route = Graph.RootGraph) {

           onBoardingNavGraph(onBoardingController)
           composable(route= Graph.MainScreenGraph){
               MainScreen(homeViewModel){
                   onChangeTheme(it)
               }
           }
       }
}

