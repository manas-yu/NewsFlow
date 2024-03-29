package com.loc.newsapp.presentation.details.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar

import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.loc.newsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onBrowsingClick: () -> Unit,
    onShareClick: () -> Unit
) {
    var isBookMarked by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = { onShareClick() }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null
                )
            }
            IconButton(onClick = {
                isBookMarked = !isBookMarked
                onBookmarkClick()

            }) {
                Icon(
                    painter = if (isBookMarked) painterResource(id = R.drawable.ic_bookmark_filled) else painterResource(
                        id = R.drawable.ic_bookmark
                    ),
                    tint = Color.Black,
                    contentDescription = null
                )
            }
            IconButton(onClick = { onBrowsingClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_network),
                    contentDescription = null
                )
            }
        },
        title = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            actionIconContentColor = colorResource(
                id = R.color.body
            ),
            containerColor = Color.Transparent,
            navigationIconContentColor = colorResource(
                id = R.color.body
            ),
        )

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    onBackClick: () -> Unit,
    onBrowsingClick: () -> Unit,
    onShareClick: () -> Unit
) {
    var isBookMarked by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = { onShareClick() }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null
                )
            }

            IconButton(onClick = { onBrowsingClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_network),
                    contentDescription = null
                )
            }
        },
        title = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            actionIconContentColor = colorResource(
                id = R.color.body
            ),
            containerColor = Color.Transparent,
            navigationIconContentColor = colorResource(
                id = R.color.body
            ),
        )

    )
}