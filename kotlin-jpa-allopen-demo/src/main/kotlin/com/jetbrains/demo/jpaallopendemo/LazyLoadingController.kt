package com.jetbrains.demo.jpaallopendemo

import org.hibernate.Hibernate
import org.hibernate.proxy.HibernateProxy
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LazyLoadingController(private val projectRepository: ProjectRepository) {

    @GetMapping("/lazy-load")
    @Transactional(readOnly = true)
    fun lazyLoadingTest(): Map<String, Any?> {
        val project = projectRepository.findAll().first()
        val client = project.client!!

        val isProxy = client is HibernateProxy
        val initializedBeforeAccess = Hibernate.isInitialized(client)
        val clientName = client.name
        val initializedAfterAccess = Hibernate.isInitialized(client)

        return mapOf(
            "project" to project.title,
            "clientIsProxy" to isProxy,
            "initializedBeforeAccess" to initializedBeforeAccess,
            "clientName" to clientName,
            "initializedAfterAccess" to initializedAfterAccess,
            "lazyLoadingWorks" to (isProxy && !initializedBeforeAccess)
        )
    }
}
