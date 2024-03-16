package com.loc.newsapp.presentation.rssfeed

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.loc.newsapp.R
import com.loc.newsapp.domain.model.Feed
import com.loc.newsapp.presentation.Dimens
import com.loc.newsapp.presentation.Dimens.ExtraSmallPadding2
import com.loc.newsapp.presentation.Dimens.MediumPadding1
import com.loc.newsapp.presentation.common.CustomTextField
import com.loc.newsapp.presentation.common.NewsButton
import com.loc.newsapp.presentation.common.NewsTextButton
import com.loc.newsapp.presentation.rssfeed.components.FeedsList
import com.loc.newsapp.presentation.rssfeed.components.RssTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RssFeedScreen(
    onBackClick: () -> Unit,
    onFollowClicked: () -> Unit,
    onUnfollowClick: (Feed) -> Unit,
    onFeedClick: (Feed) -> Unit
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    var isDialogue by remember { mutableStateOf(false) }
    var feedName by remember { mutableStateOf("") }
    var feedURL by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()

    ) {


        BottomSheetScaffold(
            sheetContent = {
                FeedsList(
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .padding(MediumPadding1),
                    feeds = listOf(
                        Feed(
                            name = "anime",
                            url = "wwd",
                            created_at = "2021-09-09T00:00:00.000Z",
                            updated_at = "2021-09-09T00:00:00.000Z",
                            id = "1",
                            user_id = "1"
                        ),
                        Feed(
                            name = "asd",
                            url = "sadasd",
                            created_at = "2021-09-09T00:00:00.000Z",
                            updated_at = "2021-09-09T00:00:00.000Z",
                            id = "1",
                            user_id = "1"
                        )
                    ), onClick = {
                        onFeedClick(it)
                    }, onUnfollowClick = {
                        onUnfollowClick(it)
                    })
            }, scaffoldState = scaffoldState
        ) {
            RssTopBar(
                onBackClick = onBackClick,
                onFollowClicked = { isDialogue = true },
                showBottomSheet = {
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                })

            Spacer(modifier = Modifier.height(Dimens.MediumPadding1))
            if (isDialogue) {
                AlertDialog(onDismissRequest = { isDialogue = false },
                    text = {
                        Column {
                            CustomTextField(
                                painter = painterResource(id = R.drawable.rss_icon),
                                placeholder = "Feed Name",
                                onValueChange = {
                                    feedName = it
                                },
                                value = feedName
                            )
                            Spacer(modifier = Modifier.height(ExtraSmallPadding2))
                            CustomTextField(
                                painter = painterResource(id = R.drawable.ic_network),
                                placeholder = "Feed URL",
                                onValueChange = {
                                    feedURL = it
                                },
                                value = feedURL
                            )
                        }
                    },
                    confirmButton = {
                        NewsButton(text = "Follow") {
                            onFollowClicked()
                            isDialogue = false
                        }
                    }, dismissButton = {
                        NewsTextButton(
                            text = "Cancel"
                        ) {
                            isDialogue = false
                        }
                    })

            }
        }


    }
}