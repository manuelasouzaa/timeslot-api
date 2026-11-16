package br.com.manuelasouzaa.timeslotapi.auth.controller

import br.com.manuelasouzaa.timeslotapi.auth.dto.RegisterRequest
import br.com.manuelasouzaa.timeslotapi.auth.dto.RegisterResponse
import br.com.manuelasouzaa.timeslotapi.auth.service.RegistrationService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val registrationService: RegistrationService
) {

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody request: RegisterRequest
    ): ResponseEntity<RegisterResponse> {
        val user = registrationService.register(request)

        val response = RegisterResponse(
            id = requireNotNull(user.id),
            cpf = user.cpf,
            fullName = user.fullName,
            email = user.email,
            phone = user.phone,
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

}
