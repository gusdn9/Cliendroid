package com.hyunwoo.cliendroid.domain.usecase

import com.hyunwoo.cliendroid.domain.model.search.type.SearchType
import com.hyunwoo.cliendroid.domain.model.search.type.SearchTypeContent
import com.hyunwoo.cliendroid.domain.repository.SearchRepository
import javax.inject.Inject

class SearchTypeUseCase @Inject constructor(
    private val searchTypeRepository: SearchRepository
) {

    suspend operator fun invoke(target: SearchType, keyword: String, page: Int): SearchTypeContent =
        searchTypeRepository.search(target, keyword, page)
}
