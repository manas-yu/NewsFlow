package com.loc.newsapp.domain.model

data class Feed(
    val id: String,
    val createdAt: String,
    val updatedAt: String,
    val name: String,
    val url: String,
    val userId: String
)