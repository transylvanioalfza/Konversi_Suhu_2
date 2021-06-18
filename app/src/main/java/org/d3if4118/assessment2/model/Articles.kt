package org.d3if4118.assessment2.model

import java.io.Serializable

data class Articles(
    val source: Source?=null,
    val author: String = "",
    val title: String="",
    val description: String="",
    val url: String="",
    val urlToImage: String="",
    val publishedAt: String="",
    val content: String=""
) : Serializable