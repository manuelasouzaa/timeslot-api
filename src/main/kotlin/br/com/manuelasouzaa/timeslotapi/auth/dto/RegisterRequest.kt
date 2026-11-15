package br.com.manuelasouzaa.timeslotapi.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank
    @field:Pattern(regexp = "\\d{11}")
    val cpf: String,

    @field:NotBlank
    val fullName: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val phone: String,

    @field:NotBlank
    @field:Size(min = 8)
    val password: String,
)
