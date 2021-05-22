package com.hyunwoo.cliendroid.data.repository

import com.hyunwoo.cliendroid.data.mapper.toSearchTypeContent
import com.hyunwoo.cliendroid.domain.model.search.type.SearchType
import com.hyunwoo.cliendroid.domain.model.search.type.SearchTypeContent
import com.hyunwoo.cliendroid.domain.repository.SearchRepository
import com.hyunwoo.cliendroid.network.service.SearchInfraService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchInfraService
) : SearchRepository {

    override suspend fun search(target: SearchType, keyword: String, page: Int): SearchTypeContent =
        searchService.search(
            target = target.value,
            keyword = keyword,
            page = page
        ).toSearchTypeContent()
}
