package com.jetbrains.demo.jpaallopendemo

import jakarta.persistence.*

@Entity
class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String = "",

    @Column
    val email: String? = null,

    @OneToMany(mappedBy = "client", cascade = [CascadeType.ALL], orphanRemoval = true)
    val projects: MutableList<Project> = mutableListOf()
) {
    override fun toString(): String = "Client(id=$id, name='$name')"
}
