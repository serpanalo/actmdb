package com.serpanalo.testshared

import com.serpanalo.domain.Movie
import com.serpanalo.domain.Video

val mockedMovie = Movie(
    5,
    "Title",
    "Overview",
    "01/01/2025",
    "",
    "",
    "",
    "",
    5.1,
    5.1,
    false
)

val mockedVideo = Video(
    "5",
    "key",
    "name",
    "site",
    0,
    "",
    "",
    "type"
)


val mockedVideo2 = Video(
    "6",
    "key",
    "name",
    "site",
    0,
    "",
    "",
    "type"
)

val mockedVideos: List<Any> = listOf(mockedMovie, mockedVideo)