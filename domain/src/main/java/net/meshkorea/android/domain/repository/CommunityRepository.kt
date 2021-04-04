package net.meshkorea.android.domain.repository

import net.meshkorea.android.domain.model.EveryOneParkForum
import net.meshkorea.android.domain.model.Result

interface CommunityRepository {

    suspend fun getEveryOneParkForum(page: Int): Result<List<EveryOneParkForum>>
}
