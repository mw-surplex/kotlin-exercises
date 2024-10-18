package functional.project

import java.time.LocalDateTime

class UserDtoFactory(
    private val timeProvider: TimeProvider,
    private val uuidGenerator: UuidGenerator,
    private val userKeyGenerator: UserKeyGenerator,
) {
    // Should produce UserDto based on AddUser
    // Should generate id using uuidGenerator
    // Should generate key using userKeyGenerator, or generate random key using uuidGenerator if userKeyGenerator returns null
    // Should set isAdmin to false
    // Should set creationTime to current time using timeProvider
    fun produceUserDto(addUser: AddUser): UserDto = TODO()
}
