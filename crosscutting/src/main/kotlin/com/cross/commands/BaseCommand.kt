package com.cross.commands

import java.time.LocalDateTime
import java.util.*

open abstract class BaseCommand {

    @Transient
    val id : UUID = UUID.randomUUID()

    @Transient
    val createdAt : LocalDateTime = LocalDateTime.now()

}