package com.cross.events

import com.cross.domain.Notification

data class DomainInvalidEvent(val notifications : List<Notification>)