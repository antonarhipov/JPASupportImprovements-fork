package com.example.jpa

import jakarta.validation.Validation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ValidationController {

    @GetMapping("/validation-test")
    fun validationTest(): Map<String, Any> {
        val validator = Validation.buildDefaultValidatorFactory().validator

        // firstName is blank, email is invalid — both should fail validation
       // val customer = Customer(firstName = "", lastName = "Smith", email = "not-an-email")
        val customer = Customer()
        val violations = validator.validate(customer)

        return mapOf(
            "violationCount" to violations.size,
            "violatedFields" to violations.map { it.propertyPath.toString() }.sorted(),
            "validationWorksOnFields" to violations.any { it.propertyPath.toString() == "firstName" }
        )
    }
}
