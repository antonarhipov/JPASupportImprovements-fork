package com.example.dataclass

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(private val repository: CustomerRepository) {

    @GetMapping
    fun findAll(): Iterable<Customer> = repository.findAll()

    @PostMapping
    fun create(@RequestBody customer: Customer): Customer = repository.save(customer)

    @GetMapping("/add-to-hash-set")
    fun hashSetTest(): Map<String, Any?> {
        val customer = Customer(firstName = "Alice", lastName = "Smith")
        val set = HashSet<Customer>()
        set.add(customer)

        val inSetBeforeSave = customer in set

        val saved = repository.save(customer)

        val inSetAfterSave = saved in set

        return mapOf(
            "inSetBeforeSave" to inSetBeforeSave,
            "inSetAfterSave" to inSetAfterSave,
            "idAssigned" to saved.id
        )
    }
}
