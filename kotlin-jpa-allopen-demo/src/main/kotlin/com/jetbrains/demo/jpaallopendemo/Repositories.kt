package com.jetbrains.demo.jpaallopendemo

import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository : JpaRepository<Client, Long>

interface ProjectRepository : JpaRepository<Project, Long>
