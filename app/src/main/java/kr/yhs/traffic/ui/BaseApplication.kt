package kr.yhs.traffic.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kr.yhs.traffic.MainActivity
import kr.yhs.traffic.theme.Screen


@OptIn(ExperimentalWearMaterialApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class)
@Composable
fun BaseApplication(activity: MainActivity) {
    val navigationController = rememberSwipeDismissableNavController()
    SwipeDismissableNavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navigationController,
        startDestination = Screen.MainScreen.route
    ) {
        // Main Windows
        composable(Screen.MainScreen.route) {
            val pagerState = rememberPagerState()
            Scaffold {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    HorizontalPager(
                        count = 4,
                        state = pagerState,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) { page: Int ->
                        if (page == 0) StationSearch()
                        if (page == 1) StationGPS()
                        if (page == 2) StationStar()
                    }
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        activeColor = Color(0xffffffff),
                        inactiveColor = Color(0xff808080),
                        indicatorWidth = 4.dp
                    )
                }
            }
        }
        // composable(Screen.StationList.route) {
        //     StationList()
        // }
    }
}