package es.jarroyo.tddweatherapp.domain.model.user

object UserFactory {
    val userIdTest = 1L
    val userNameTest = "userTest"

    fun createUserTest(): User {
        return User(userIdTest, userNameTest)
    }
}