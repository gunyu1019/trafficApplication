package kr.yhs.traffic.ui

import android.app.Activity
import android.app.RemoteInput
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.widget.ConfirmationOverlay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.yhs.traffic.MainActivity
import kr.yhs.traffic.R
import kr.yhs.traffic.models.StationInfo
import kr.yhs.traffic.module.getLocation
import kr.yhs.traffic.ui.pages.*
import retrofit2.await


@OptIn(ExperimentalWearMaterialApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class)
@Composable
fun ComposeApp(activity: MainActivity) {
    var stationQuery by remember { mutableStateOf("") }
    val navigationController = rememberSwipeDismissableNavController()
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == Activity.RESULT_OK) {
            val intent = it.data
            val remoteInputResponse = RemoteInput.getResultsFromIntent(intent)
            stationQuery = remoteInputResponse.getString("SEARCH_BUS_STATION", "")
            navigationController.navigate(
                Screen.StationList.route + "?$STATION_TYPE=${StationListType.SEARCH}",
            )
        }
    }

    SwipeDismissableNavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navigationController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(Screen.MainScreen.route) {
            mainPage(
                listOf({
                        StationSearch {
                            val remoteInputs = listOf(
                                RemoteInput.Builder("SEARCH_BUS_STATION")
                                    .setLabel("검색하실 정류소")
                                    .build()
                            )
                            val intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
                            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)
                            launcher.launch(intent)
                        }
                    }, {
                        StationGPS {
                            navigationController.navigate(
                                Screen.StationList.route + "?$STATION_TYPE=${StationListType.GPS_LOCATION_SEARCH}",
                            )
                        }
                    }, {
                        StationStar {}
                    }
                )
            )
        }
        composable(
            Screen.StationList.route+ "?$STATION_TYPE={$STATION_TYPE}",
            listOf(
                navArgument(STATION_TYPE) {
                    type = NavType.EnumType(StationListType::class.java)
                    defaultValue = StationListType.SEARCH
                }
            )
        ) {
            val stationType = it.arguments?.getSerializable(STATION_TYPE)
            var stationList by remember { mutableStateOf<List<StationInfo>>(emptyList()) }
            var location by remember { mutableStateOf<Location?>(null) }
            LaunchedEffect(true) {
                location = getLocation(activity, activity.fusedLocationClient!!)
                stationList = withContext(Dispatchers.Default) {
                    when (stationType) {
                        StationListType.SEARCH -> {
                            activity.client!!.getStation(
                                name = stationQuery
                            ).await()
                        }
                        StationListType.GPS_LOCATION_SEARCH -> {
                            val convertData = mutableListOf<StationInfo>()
                            if (location == null) {
                                ConfirmationOverlay()
                                    .setType(ConfirmationOverlay.FAILURE_ANIMATION)
                                    .setMessage(activity.getText(R.string.gps_not_found))
                                    .showOn(activity)
                                navigationController.navigate(
                                    Screen.MainScreen.route
                                )
                            }
                            val stationAroundList = activity.client!!.getStationAround(
                                posX = location!!.longitude,
                                posY = location!!.latitude
                            ).await()
                            for (st in stationAroundList) {
                                convertData.add(
                                    st.convertToStationInfo()
                                )
                            }
                            convertData
                        }
                        else -> listOf()
                    }
                }
            }
            if (activity.fusedLocationClient == null) {
                ConfirmationOverlay()
                    .setType(ConfirmationOverlay.FAILURE_ANIMATION)
                    .setMessage(activity.getText(R.string.gps_not_found))
                    .showOn(activity)
                navigationController.navigate(
                    Screen.MainScreen.route
                )
                return@composable
            }

            val title = when (stationType) {
                StationListType.SEARCH -> activity.getString(R.string.title_search, stationQuery)
                StationListType.GPS_LOCATION_SEARCH -> activity.getString(R.string.title_gps_location)
                else -> activity.getString(R.string.title_search)
            }
            StationList(title, stationList, location) { station: StationInfo ->
                navigationController.navigate(
                    Screen.StationList.route + "?$STATION_REGION=${station.type}&$STATION_ID=${station.id}",
                )
            }
        }
        composable(
            Screen.StationInfo.route+ "?$STATION_REGION={$STATION_REGION}&$STATION_ID={$STATION_ID}",
            listOf(
                navArgument(STATION_REGION) {
                    type = NavType.IntType
                },
                navArgument(STATION_ID) {
                    type = NavType.IntType
                }
            )
        ) {

        }
    }
}
