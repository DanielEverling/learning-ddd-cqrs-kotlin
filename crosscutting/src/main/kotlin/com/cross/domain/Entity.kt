package com.cross.domain


import java.util.*
import java.lang.RuntimeException

class ResultEntityException(override val message : String) : RuntimeException(message)

sealed class ResultEntity<out L, out R> {
    class Failure(val notifications: List<Notification>) : ResultEntity<List<Notification>, Nothing>()
    class Success<T : Entity>(val entity : T) : ResultEntity<Nothing, T>()

    fun notifications() : List<Notification> {
        return when(this) {
            is Failure -> notifications
            else -> throw ResultEntityException("Result Entity is with success")
        }
    }

    fun entity() : R {
        return when(this) {
            is Success -> entity
            else -> throw ResultEntityException("Result Entity is with fail")
        }
    }

    override fun toString(): String {
        return when(this) {
            is Success -> entity.toString()
            is Failure -> notifications.toString()
        }
    }
}

open abstract class Entity : ValidatorsAware {

    private val _notifications: MutableCollection<Optional<Notification>> = mutableListOf()

    val notifications : List<Notification>
        get() {
            return _notifications.map { it.get() }
        }

    protected fun validate() {
         validators().filter { it.isPresent }.toCollection(_notifications)
    }

    protected fun isValid(): Boolean = _notifications.isEmpty()

}