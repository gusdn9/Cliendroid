package com.hyunwoo.cliendroid.network

import com.hyunwoo.cliendroid.network.model.everyonepark.BlockedCommentDto
import com.hyunwoo.cliendroid.network.model.everyonepark.CommentDto
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class BoardServiceUnitTest : BaseUnitTest() {

    @Test
    fun requestList() = runBlocking {
        val forums = network.provideCommunityService().getEveryoneParkForumList(0)
        print(forums.contents.size)
    }

    @Test
    fun requestDetail() = runBlocking {
        val url = "/service/board/park/16061901"
        val res = network.provideCommunityService().getEveryoneParkForumDetail(url)
        println(res.htmlBody)
        res.comments.forEach { comment ->
            when (comment) {
                is CommentDto -> println(comment.contents)
                is BlockedCommentDto -> println(comment.contents)
            }
        }
    }

    @Test
    fun search() = runBlocking {
        val service = network.provideCommunityService()
        val result = service.search(keyword = "3080", sort = "accuracy", page = 1)
        print(result.contents.size)
    }
}
