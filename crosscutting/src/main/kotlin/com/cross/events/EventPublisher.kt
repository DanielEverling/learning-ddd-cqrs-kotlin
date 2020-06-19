package com.cross.events

import com.cross.infra.Log
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Event

@ApplicationScoped
class EventPublisher (val event: Event<Any>) {

    val log = Log(EventPublisher::class.java)

    fun publisher(any: Any) {
        log.info(message = "Publishing event $any")
        event.fire(any)
    }

}