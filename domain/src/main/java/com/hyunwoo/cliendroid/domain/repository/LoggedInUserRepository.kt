package com.hyunwoo.cliendroid.domain.repository

import com.hyunwoo.cliendroid.domain.event.EventChannel
import com.hyunwoo.cliendroid.domain.event.LoggedInEvent
import com.hyunwoo.cliendroid.domain.event.LoggedInUserUpdatedEvent
import com.hyunwoo.cliendroid.domain.event.LoggedOutEvent
import com.hyunwoo.cliendroid.domain.model.LoggedInUser
import com.hyunwoo.cliendroid.domain.model.LogoutCause
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class LoggedInUserRepository @Inject constructor(
    private val dataSource: LoggedInUserDataSource,

    @Named("LoggedInChannel")
    private val loggedInChannel: EventChannel<LoggedInEvent>,

    @Named("LoggedInUserUpdatedChannel")
    private val loggedInUserUpdatedChannel: EventChannel<LoggedInUserUpdatedEvent>,

    @Named("LoggedOutChannel")
    private val loggedOutChannel: EventChannel<LoggedOutEvent>
) {

    val loggedInUser: LoggedInUser?
        get() = synchronized(this) {
            dataSource.loggedInUser
        }

    @Synchronized
    fun login(loggedInUser: LoggedInUser) {
        dataSource.loggedInUser = loggedInUser
        notifyLoggedIn(loggedInUser)
    }

    @Synchronized
    fun update(block: LoggedInUser.() -> LoggedInUser) {
        val prevUser = dataSource.loggedInUser
        if(prevUser != null) {
            val currUser = block(prevUser)
            dataSource.loggedInUser = currUser
            notifyUpdated(prevUser, currUser)
        }
    }

    @Synchronized
    fun logout(cause: LogoutCause): LoggedInUser? {
        val prevUser = dataSource.loggedInUser
        if (prevUser != null) {
            dataSource.loggedInUser = null
            notifyLoggedOut(cause)
        }
        return prevUser
    }

    private fun notifyLoggedIn(user: LoggedInUser) {
        loggedInChannel.send(LoggedInEvent(user))
    }

    private fun notifyUpdated(prev: LoggedInUser, curr: LoggedInUser) {
        loggedInUserUpdatedChannel.send(LoggedInUserUpdatedEvent(prev, curr))
    }

    private fun notifyLoggedOut(cause: LogoutCause) {
        loggedOutChannel.send(LoggedOutEvent(cause))
    }
}
