package com.hyunwoo.cliendroid.domain.usecase

import com.hyunwoo.cliendroid.domain.model.BaseEveryoneParkForum
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum
import com.hyunwoo.cliendroid.domain.repository.CommunityRepository
import javax.inject.Inject

class GetEveryoneParkForumListUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {

    suspend operator fun invoke(page: Int): List<BaseEveryoneParkForum> =
        communityRepository.getEveryoneParkForumList(page)
}
