package com.hyunwoo.cliendroid.network

import com.hyunwoo.cliendroid.network.model.BlockedCommentDto
import com.hyunwoo.cliendroid.network.model.CommentDto
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    // @Test
    // fun requestList() = runBlocking {
    //     val network = NetworkProvider.create(HostType.PROD, true)
    //     val forums = network.provideCommunityService().getEveryoneParkForumList(0)
    //     print(forums.contents.size)
    // }

    @Test
    fun requestDetail() = runBlocking {
        val network = NetworkProvider.create(HostType.PROD, true)
        val url = "/service/board/park/16055998"
        val res = network.provideCommunityService().getEveryoneParkForumDetail(url)
        println(res.htmlBody)
        res.comments.forEach { comment ->
            when(comment) {
                is CommentDto -> println(comment.contents)
                is BlockedCommentDto -> println(comment.contents)
            }
        }
    }
}
