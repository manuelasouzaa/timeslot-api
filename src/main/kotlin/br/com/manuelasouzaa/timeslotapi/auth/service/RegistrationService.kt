package br.com.manuelasouzaa.timeslotapi.auth.service

import br.com.manuelasouzaa.timeslotapi.auth.exception.EmailAlreadyRegisteredException
import br.com.manuelasouzaa.timeslotapi.auth.repository.UserRepository

class RegistrationService(
    private val repository: UserRepository
) {

    fun ensureEmailIsAvailable(email: String) {
        if (repository.existsByEmail(email)) {
            throw EmailAlreadyRegisteredException()
        }
    }

}
