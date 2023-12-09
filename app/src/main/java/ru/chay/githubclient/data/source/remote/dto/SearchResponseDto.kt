package ru.chay.githubclient.data.source.remote.dto

import androidx.annotation.Keep

@Keep
data class SearchResponseDto(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<UserDto>
)