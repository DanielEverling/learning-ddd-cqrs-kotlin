package com.learning.ddd.application.commons

import com.cross.events.DomainInvalidEvent
import com.cross.events.EntityNotFoundEvent
import com.nhaarman.mockitokotlin2.any
import java.util.*

fun anyUUID() : UUID = any()
fun anyDomainInvalidEvent() : DomainInvalidEvent = any()
fun anyEntityNotFoundEvent() : EntityNotFoundEvent = any()