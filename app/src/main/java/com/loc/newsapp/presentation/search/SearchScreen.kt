package com.loc.newsapp.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.paging.compose.collectAsLazyPagingItems
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.presentation.Dimens.MediumPadding1
import com.loc.newsapp.presentation.common.ArticlesList
import com.loc.newsapp.presentation.common.SearchBar

@Composable
fun SearchScreen(
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Article) -> Unit
) {
    val focusManager = LocalContext.current.getSystemService(FocusManager::class.java)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(MediumPadding1)
    ) {
        SearchBar(
            readOnly = false,
            onValueChange = {

                event(SearchEvent.UpdateSearchQuery(it))
            },
            value = state.searchQuery,
            onSearch = {
                focusManager?.clearFocus()
                event(SearchEvent.SearchNews)
            })
        Spacer(modifier = Modifier.height(MediumPadding1))
        state.articles?.let { it ->
            val articles = it.collectAsLazyPagingItems()
            ArticlesList(articles = articles, onClick = { article -> navigateToDetails(article) })
        }
    }

}