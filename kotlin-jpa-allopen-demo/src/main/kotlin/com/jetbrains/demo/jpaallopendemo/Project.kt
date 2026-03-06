package com.jetbrains.demo.jpaallopendemo

import jakarta.persistence.*

@Entity
class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val title: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    val client: Client? = null
) {
    override fun toString(): String = "Project(id=$id, title='$title')"
}
