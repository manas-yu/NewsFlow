package com.loc.newsapp.presentation.navgraph


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign


import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.loc.newsapp.presentation.onboarding.OnBoardingScreen


import com.loc.newsapp.presentation.onboarding.OnBoardingViewModel


@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {

                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(event = viewModel::onEvent)
            }
        }
        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigatorScreen.route
        ) {
            composable(route = Route.NewsNavigatorScreen.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "NewsNavigatorScreen", textAlign = TextAlign.Center)
                }

            }
        }
    }
}