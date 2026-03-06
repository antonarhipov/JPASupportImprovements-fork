package com.example.jpa

import jakarta.validation.Validation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ValidationTest {

    @Test
    fun `validation annotations should detect violations on fields`() {
        val validator = Validation.buildDefaultValidatorFactory().validator

        val customer = Customer(firstName = "", lastName = "Smith", email = "not-an-email")
        val violations = validator.validate(customer)
        val fields = violations.map { it.propertyPath.toString() }

        // Without -Xannotation-default-target=param-property: violations is EMPTY
        // (annotations only on constructor params, not on fields)
        // With the flag: violations contains "firstName" and "email"
        assertThat(fields).contains("firstName", "email")
    }
}
