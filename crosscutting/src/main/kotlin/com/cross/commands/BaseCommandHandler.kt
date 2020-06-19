package com.cross.commands

import com.cross.events.EventPublisher
import javax.inject.Inject

open abstract class BaseCommandHandler<T : BaseCommand> {

    @Inject
    lateinit var eventPublisher: EventPublisher

    abstract fun handler(command: T)

}