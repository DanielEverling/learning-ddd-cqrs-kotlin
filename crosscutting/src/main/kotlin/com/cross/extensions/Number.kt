package com.cross.extensions

import com.cross.domain.Notification
import java.util.Optional

fun Number.valueBiggerThanZero(message : String) : Optional<Notification> {
    return when (this.toLong() > 0) {
        true -> Optional.empty()
        else -> Optional.of(Notification(notification = message))
    }
}