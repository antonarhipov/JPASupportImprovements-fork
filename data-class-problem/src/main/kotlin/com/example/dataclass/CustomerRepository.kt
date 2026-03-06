package com.example.dataclass

import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<Customer, Long>
