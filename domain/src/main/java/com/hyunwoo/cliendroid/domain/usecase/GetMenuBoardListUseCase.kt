package com.hyunwoo.cliendroid.domain.usecase

import com.hyunwoo.cliendroid.domain.model.MenuBoards
import com.hyunwoo.cliendroid.domain.repository.CommunityRepository
import javax.inject.Inject

class GetMenuBoardListUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {

    suspend operator fun invoke(): MenuBoards =
        communityRepository.getMenuList()
}
