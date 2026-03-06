package com.jetbrains.demo.jpaallopendemo

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class JpaAllopenDemoApplication {

    @Bean
    fun seedData(clientRepository: ClientRepository, projectRepository: ProjectRepository) = CommandLineRunner {
        val client = clientRepository.save(Client(name = "Acme Corp", email = "contact@acme.com"))
        projectRepository.save(Project(title = "Website Redesign", client = client))
        projectRepository.save(Project(title = "Mobile App", client = client))
    }
}

fun main(args: Array<String>) {
    runApplication<JpaAllopenDemoApplication>(*args)
}
