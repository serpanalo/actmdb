package com.serpanalo.actmdb.data

import com.serpanalo.domain.Movie
import com.serpanalo.domain.Video

import com.serpanalo.actmdb.data.database.Movie as DomainMovie
import com.serpanalo.actmdb.data.server.Movie as ServerMovie

import com.serpanalo.actmdb.data.database.Video as DomainVideo
import com.serpanalo.actmdb.data.server.Video as ServerVideo

fun Movie.toRoomMovie(): DomainMovie =
    DomainMovie(
        id,
        idm,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        favorite
    )

fun DomainMovie.toDomainMovie(): Movie = Movie(
    id,
    idm,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)

fun ServerMovie.toDomainMovie(): Movie =
    Movie(
        0,
        idm,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath ?: posterPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        false
    )


fun Video.toRoomVideo(idm : String): DomainVideo =
    DomainVideo(
        id,
        idm,
        key,
        name,
        site,
        size,
        type,
        getVideoUrl(key),
        getThumbUrl(key)
    )

fun DomainVideo.toDomainVideo(): Video = Video(
    id,
    key,
    name,
    site,
    size,
    type,
    urlPlayback,
    urlThumbnail
)

fun ServerVideo.toDomainVideo(): Video =
    Video(
        id,
        key,
        name,
        site,
        size,
        type,
        urlPlayback,
        urlThumbnail
    )

fun getVideoUrl(key: String):String{
    return "https://www.youtube.com/watch?v=$key"
}

fun getThumbUrl(key: String):String{
    return "https://img.youtube.com/vi/$key/0.jpg"
}