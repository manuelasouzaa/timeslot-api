package br.com.manuelasouzaa.timeslotapi.auth.service

import br.com.manuelasouzaa.timeslotapi.auth.dto.RegisterRequest
import br.com.manuelasouzaa.timeslotapi.auth.entity.User
import br.com.manuelasouzaa.timeslotapi.auth.exception.EmailAlreadyRegisteredException
import br.com.manuelasouzaa.timeslotapi.auth.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class RegistrationServiceTest {

    private val userRepository: UserRepository = mock()
    private val passwordEncoder: PasswordEncoder = mock()

    private val registrationService = RegistrationService(
        repository = userRepository,
        passwordEncoder = passwordEncoder
    )

    private val validRequest = RegisterRequest(
        cpf = "12345678901",
        fullName = "Mary Anne",
        email = "mary@example.com",
        phone = "14999999999",
        password = "password123"
    )

    @Test
    fun `should register user with hashed password`() {
        whenever(userRepository.existsByEmail(validRequest.email)).thenReturn(false)

        whenever(passwordEncoder.encode(validRequest.password)).thenReturn("hashed-password")

        whenever(userRepository.save(any())).thenAnswer {
            it.getArgument<User>(0)
        }

        val result = registrationService.register(validRequest)

        assertEquals(validRequest.email, result.email)
        assertEquals(validRequest.cpf, result.cpf)
        assertEquals("hashed-password", result.passwordHash)
        assertNotEquals(validRequest.password, result.passwordHash)

        verify(userRepository).save(any())
        verify(passwordEncoder).encode(validRequest.password)
    }

    @Test
    fun `should throw exception when email is already registered`() {
        whenever(userRepository.existsByEmail(validRequest.email)).thenReturn(true)

        assertThrows(EmailAlreadyRegisteredException::class.java) {
            registrationService.register(validRequest)
        }

        verify(userRepository, never()).save(any())
        verify(passwordEncoder, never()).encode(any())
    }

}
