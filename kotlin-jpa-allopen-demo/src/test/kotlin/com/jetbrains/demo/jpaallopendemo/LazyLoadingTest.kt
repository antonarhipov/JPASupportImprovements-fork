package com.jetbrains.demo.jpaallopendemo

import jakarta.persistence.EntityManager
import org.hibernate.Hibernate
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@SpringBootTest
@Transactional
class LazyLoadingTest {

    @Autowired lateinit var clientRepository: ClientRepository
    @Autowired lateinit var projectRepository: ProjectRepository
    @Autowired lateinit var entityManager: EntityManager

    @Test
    fun `client should be lazily loaded when accessing project`() {
        // 1. Save a client and a project
        val client = clientRepository.save(Client(name = "Acme Corp", email = "contact@acme.com"))
        projectRepository.save(Project(title = "Website Redesign", client = client))

        // 2. Clear persistence context — next load hits the DB
        entityManager.flush()
        entityManager.clear()

        // 3. Load the project
        val project = projectRepository.findAll().first()

        // 4. Client is NOT loaded yet — it's a Hibernate proxy
        assertFalse(Hibernate.isInitialized(project.client))

        // 5. Access client name — triggers lazy loading
        assertEquals("Acme Corp", project.client!!.name)

        // 6. Now it's loaded
        assertTrue(Hibernate.isInitialized(project.client))
    }
}
