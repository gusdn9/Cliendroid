package com.hyunwoo.cliendroid.domain.usecase

import com.hyunwoo.cliendroid.domain.model.EveryoneParkForumContent
import com.hyunwoo.cliendroid.domain.repository.CommunityRepository
import javax.inject.Inject

class GetEveryoneParkForumDetailUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {

    suspend operator fun invoke(detailUrl: String): EveryoneParkForumContent =
        communityRepository.getEveryoneParkForumDetail(detailUrl)
}
