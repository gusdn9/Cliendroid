package com.hyunwoo.cliendroid.domain.usecase

import com.hyunwoo.cliendroid.domain.model.SearchContent
import com.hyunwoo.cliendroid.domain.model.SearchSort
import com.hyunwoo.cliendroid.domain.repository.CommunityRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {

    suspend operator fun invoke(keyword: String, page: Int, sort: SearchSort?, boardId: String? = null): SearchContent =
        communityRepository.search(keyword, page, sort, boardId)
}
