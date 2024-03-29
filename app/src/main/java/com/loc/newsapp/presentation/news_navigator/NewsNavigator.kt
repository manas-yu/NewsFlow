package com.loc.newsapp.presentation.news_navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.loc.newsapp.MainViewModel
import com.loc.newsapp.R
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.model.Post
import com.loc.newsapp.presentation.bookmark.BookMarkViewModel
import com.loc.newsapp.presentation.bookmark.BookmarkScreen
import com.loc.newsapp.presentation.details.DetailsScreen
import com.loc.newsapp.presentation.details.DetailsViewModel
import com.loc.newsapp.presentation.home.HomeScreen
import com.loc.newsapp.presentation.home.HomeViewModel
import com.loc.newsapp.presentation.login.LoginScreen
import com.loc.newsapp.presentation.login.LoginViewModel
import com.loc.newsapp.presentation.navgraph.Route

import com.loc.newsapp.presentation.news_navigator.components.BottomNavigationItem
import com.loc.newsapp.presentation.news_navigator.components.NewsBottomNavigation
import com.loc.newsapp.presentation.rssfeed.RssFeedScreen
import com.loc.newsapp.presentation.rssfeed.RssViewModel
import com.loc.newsapp.presentation.search.SearchScreen
import com.loc.newsapp.presentation.search.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmarks")
        )
    }
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route || backStackState?.destination?.route == Route.SearchScreen.route || backStackState?.destination?.route == Route.BookmarkScreen.route
    }
    selectedItem = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarkScreen.route -> 2
        else -> 3
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (selectedItem == 0) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Route.RssFeedScreen.route)
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    // FAB content, such as an Icon
                    Icon(
                        painter = painterResource(id = R.drawable.rss_icon),
                        contentDescription = "rss"
                    )
                }
            }
        },
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem, onClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                route = Route.BookmarkScreen.route
                            )
                        }
                    }
                )
            }
        }) { it ->
        val mainViewModel: MainViewModel = hiltViewModel()
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            //TODO: handel auto login
            startDestination = mainViewModel.loginStatusDestination.value,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.RssFeedScreen.route) {
                val viewModel: RssViewModel = hiltViewModel()
                val state = viewModel.state.value
                RssFeedScreen(
                    state = state,
                    onBackClick = { navController.navigateUp() },
                    event = viewModel::onEvent,
                    navigateToDetails = { post ->
                        navigateToPostDetails(navController, post)
                    }
                )

            }
            composable(route = Route.LoginScreen.route) {
                //TODO: Add LoginScreen
                val viewModel: LoginViewModel = hiltViewModel()
                val state = viewModel.state.value
                LoginScreen(
                    navigateToHomeState = viewModel.navigateToHomeState,
                    state = state,
                    event = viewModel::onEvent,
                    sideEffect = viewModel.sideEffect, navigateToHome = {
                        navController.popBackStack()
                        navController.navigate(Route.HomeScreen.route)
                    }
                )
            }
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    event = viewModel::onEvent,
                    articles = articles, navigateToSearch = {
                        navigateToTab(navController, Route.SearchScreen.route)
                    }, navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    }, onLogout = {

                        navController.popBackStack()
                        navController.navigate(Route.LoginScreen.route)
                    })
            }
            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(state = state, event = viewModel::onEvent) { article ->
                    navigateToDetails(navController, article)
                }
            }
            composable(route = Route.DetailScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        DetailsScreen(
                            article = article,
                            event = viewModel::onEvent,
                            navigateUp = { navController.navigateUp() },
                            sideEffect = viewModel.sideEffect
                        )
                    }

            }
            composable(route = Route.PostDetailScreen.route) {

                navController.previousBackStackEntry?.savedStateHandle?.get<Post?>("post")
                    ?.let { post ->
                        DetailsScreen(
                            post = post,

                            navigateUp = { navController.navigateUp() },

                            )
                    }

            }
            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookMarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(state = state) {
                    navigateToDetails(navController = navController, article = it)

                }
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(Route.DetailScreen.route)
}

private fun navigateToPostDetails(navController: NavController, post: Post) {
    navController.currentBackStackEntry?.savedStateHandle?.set("post", post)
    navController.navigate(Route.PostDetailScreen.route)
}