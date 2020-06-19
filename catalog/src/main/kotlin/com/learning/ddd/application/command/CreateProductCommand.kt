package com.learning.ddd.application.command

import com.cross.commands.BaseCommand
import java.math.BigDecimal
import java.util.*

data class CreateProductCommand (val name: String, val description: String, val category: UUID, val value: BigDecimal) : BaseCommand()