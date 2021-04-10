package com.hyunwoo.cliendroid.network

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
    //     val forumList = network.provideCommunityService().getEveryoneParkForumList(0)
    //     print(forumList.size)
    // }

    @Test
    fun requestDetail() = runBlocking {
        val network = NetworkProvider.create(HostType.PROD, true)
        val res = network.provideCommunityService().getEveryoneParkForumDetail(16053544)
        print(res)
    }
}
