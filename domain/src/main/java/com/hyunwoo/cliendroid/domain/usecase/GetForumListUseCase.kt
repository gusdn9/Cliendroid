package com.hyunwoo.cliendroid.domain.usecase

import com.hyunwoo.cliendroid.domain.model.BaseEveryoneParkForum
import com.hyunwoo.cliendroid.domain.repository.CommunityRepository
import javax.inject.Inject

class GetForumListUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {

    suspend operator fun invoke(link: String, page: Int): List<BaseEveryoneParkForum> =
        communityRepository.getForumList(link, page)
}
