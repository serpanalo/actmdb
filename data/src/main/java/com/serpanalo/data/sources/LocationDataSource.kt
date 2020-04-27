package com.serpanalo.data.sources

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}