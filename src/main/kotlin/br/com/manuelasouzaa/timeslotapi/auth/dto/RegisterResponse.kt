package br.com.manuelasouzaa.timeslotapi.auth.dto

data class RegisterResponse(
    val id: Long,
    val cpf: String,
    val fullName: String,
    val email: String,
    val phone: String,
)
