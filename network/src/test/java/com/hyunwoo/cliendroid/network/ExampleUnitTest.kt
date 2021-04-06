package com.hyunwoo.cliendroid.network

import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun test() = runBlocking {
        val network = NetworkProvider.create(HostType.PROD, true)
        val forumList = network.provideCommunityService().getEveryOneParkForum(0)
        print(forumList.size)
    }
}
