package functional.project

class UserService(
    private val userRepository: UserRepository,
    private val userDtoFactory: UserDtoFactory,
    private val tokenRepository: TokenRepository,
    private val logger: Logger,
) {
    // Cache definition, that should set clearAfterWrite and clearAfterRead both to 1 minute,
    // and load function to load user by id from userRepository,
    // also loaded user should be stored in userByKeyCache
    private val userByIdCache: Cache<String, User> = cache {
        // TODO
    }

    // Cache definition, that should set clearAfterWrite and clearAfterRead both to 1 minute,
    // and load function to load user by key from userRepository,
    // also loaded user should be stored in userByIdCache
    private val userByKeyCache: Cache<String, User> = cache {
        // TODO
    }

    // Should get user from cache
    fun getUser(id: String): User? = TODO()

    // Should get user from cache
    fun getUserByKey(key: String): User? = TODO()

    // Should load user from repository, and if password matches, 
    // create, token and return it,
    // otherwise throw error with message "Wrong email or password"
    fun getToken(email: String, passwordHash: String): String = TODO()

    // Should update user in repository, and clear cache
    // should log "User updated: $user"
    // in case of user not found, should throw error with the message "User not found"
    fun updateUser(token: String, userPatch: UserPatch): User = TODO()

    // Should add user to repository if token belongs to admin,
    // should log "User added: $user"
    fun addUser(token: String, addUser: AddUser): User = TODO()

    // Should return statistics about users
    // in case of token not belonging to admin, should throw error with the message "Only admin can get statistics"
    fun userStatistics(token: String): UserStatistics = TODO()

    fun clearCache() {
        userByIdCache.clear()
        userByKeyCache.clear()
    }
}
