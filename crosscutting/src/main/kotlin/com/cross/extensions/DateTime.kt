package com.cross.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toPatternDDMMYYYY() : String = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").toString()