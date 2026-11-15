
package br.com.manuelasouzaa.timeslotapi.auth.dto

import jakarta.validation.Validation
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class RegisterRequestTest {

    private val validator = Validation
        .buildDefaultValidatorFactory()
        .validator

    private val validRequest = RegisterRequest(
        cpf = "12345678901",
        fullName = "Maria Silva",
        email = "maria@example.com",
        phone = "14999999999",
        password = "password123"
    )

    @Test
    fun `valid registration data passes validation`() {
        val violations = validator.validate(validRequest)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `invalid email is rejected`() {
        val request = validRequest.copy(email = "invalid-email")

        val violations = validator.validate(request)

        assertTrue(violations.any { it.propertyPath.toString() == "email" })
    }

    @Test
    fun `CPF must contain exactly 11 digits`() {
        val request = validRequest.copy(cpf = "12345")

        val violations = validator.validate(request)

        assertTrue(violations.any { it.propertyPath.toString() == "cpf" })
    }

    @Test
    fun `password must contain at least 8 characters`() {
        val request = validRequest.copy(password = "short")

        val violations = validator.validate(request)

        assertTrue(violations.any { it.propertyPath.toString() == "password" })
    }

    @Test
    fun `required fields cannot be blank`() {
        val request = validRequest.copy(
            cpf = "",
            fullName = " ",
            email = "",
            phone = "",
            password = ""
        )

        val violatedFields = validator.validate(request)
            .map { it.propertyPath.toString() }
            .toSet()

        assertTrue(
            violatedFields.containsAll(
                setOf("cpf", "fullName", "email", "phone", "password")
            )
        )
    }
}
