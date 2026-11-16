package br.com.manuelasouzaa.timeslotapi.auth.service

import br.com.manuelasouzaa.timeslotapi.auth.dto.RegisterRequest
import br.com.manuelasouzaa.timeslotapi.auth.entity.User
import br.com.manuelasouzaa.timeslotapi.auth.exception.EmailAlreadyRegisteredException
import br.com.manuelasouzaa.timeslotapi.auth.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegistrationService(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun register(request: RegisterRequest): User {
        ensureEmailIsAvailable(request.email)

        val user = User(
            cpf = request.cpf,
            fullName = request.fullName,
            email = request.email,
            phone = request.phone,
            passwordHash = requireNotNull(passwordEncoder.encode(request.password))
        )

        return repository.save(user)
    }

    private fun ensureEmailIsAvailable(email: String) {
        if (repository.existsByEmail(email)) {
            throw EmailAlreadyRegisteredException()
        }
    }

}
