package br.com.manuelasouzaa.timeslotapi.auth.repository

import br.com.manuelasouzaa.timeslotapi.auth.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun existsByEmail(email: String): Boolean

}
