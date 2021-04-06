package com.hyunwoo.cliendroid.domain.usecase

import com.hyunwoo.cliendroid.domain.model.EveryOneParkForum
import com.hyunwoo.cliendroid.domain.repository.CommunityRepository
import javax.inject.Inject

class GetEveryOneParkForumListUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {

    suspend operator fun invoke(page: Int): List<EveryOneParkForum> =
        communityRepository.getEveryOneParkForum(page)
}
