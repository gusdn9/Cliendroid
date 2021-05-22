package com.hyunwoo.cliendroid.domain.repository

import com.hyunwoo.cliendroid.domain.model.search.type.SearchType
import com.hyunwoo.cliendroid.domain.model.search.type.SearchTypeContent

interface SearchRepository {

    suspend fun search(
        target: SearchType,
        keyword: String,
        page: Int
    ): SearchTypeContent
}
