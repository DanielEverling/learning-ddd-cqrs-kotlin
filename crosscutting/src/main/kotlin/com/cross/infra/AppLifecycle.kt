package com.cross.infra

import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import org.jetbrains.exposed.sql.Database
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.sql.DataSource

@ApplicationScoped
class AppLifecycle (val dataSource: DataSource) {

    private val log = Log(AppLifecycle::class.java)

    fun onStart(@Observes ev: StartupEvent?) {
        log.info("The application is starting...")
        Database.connect(dataSource)
    }

    fun onStop(@Observes ev: ShutdownEvent?) {
        log.info("The application is stopping...")
    }
}