package com.cross.extensions

import java.util.*


public fun <T> Optional<T>.getOrEmpty(): Optional<T> {
  return when (this.isPresent) {
      true -> Optional.of(this.get())
      else -> Optional.empty()
    }
}

public fun <T> Iterable<T>.firstOrOptional(): Optional<T> {
    when (this) {
        is List -> {
            return if (isEmpty())
                Optional.empty<T>()
            else
                Optional.of(this[0])
        }
        else -> {
            val iterator = iterator()
            if (!iterator.hasNext())
                return Optional.empty<T>()
            return Optional.of(iterator.next())
        }
    }
}