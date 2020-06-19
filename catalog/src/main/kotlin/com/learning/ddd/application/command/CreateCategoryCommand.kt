package com.learning.ddd.application.command

import com.cross.commands.BaseCommand

data class CreateCategoryCommand(val name : String) : BaseCommand()