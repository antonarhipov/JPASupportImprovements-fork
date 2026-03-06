package com.example.dataclass

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@DataJpaTest
class DataClassProblemTest {

    @Autowired
    lateinit var repository: CustomerRepository

    @Test
    fun `data class entity disappears from HashSet after save`() {
        val customer = Customer(firstName = "Alice", lastName = "Smith")
        val set = HashSet<Customer>()
        set.add(customer)

        assertTrue(customer in set)

        val saved = repository.save(customer)

        assertFalse(saved in set, "data class entity disappears — hashCode changed after id was assigned")
    }
}
