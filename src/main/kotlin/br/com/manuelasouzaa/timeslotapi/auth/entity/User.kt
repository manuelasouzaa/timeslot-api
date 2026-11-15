package br.com.manuelasouzaa.timeslotapi.auth.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @field:Column(nullable = false, length = 11)
    var cpf: String,

    @field:Column(nullable = false)
    var fullName: String,

    @field:Column(nullable = false, unique = true)
    var email: String,

    @field:Column(nullable = false)
    var phone: String,

    @field:Column(nullable = false)
    var passwordHash: String,
) {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
