package com.yourname.redgifboard

data class TokenResponse(
    val token: String
)

data class GifUrls(
    val sd: String,
    val thumbnail: String? = null,
    val hd: String
)

data class GifItem(
    val id: String,
    val urls: GifUrls
)

data class SearchResponse(
    val gifs: List<GifItem>
)
