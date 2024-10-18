package functional.context

import kotlin.random.Random

class PetStore(
    private val database: Database,
) {
    fun addPet(
        addPetRequest: AddPetRequest,
    ): Pet? {
        return try {
            database.addPet(addPetRequest)
        } catch (e: InsertionConflictException) {
            null
        } catch (e: Exception) {
            null
        }
    }
}

data class AddPetRequest(val name: String)
data class Pet(val id: Int, val name: String)
class InsertionConflictException : Exception()

interface Database {
    fun addPet(addPetRequest: AddPetRequest): Pet
}

interface Logger {
    fun logInfo(message: String)
    fun logWarning(message: String)
    fun logError(message: String)
}

fun main(): Unit = with(ConsoleLogger()) {
    val database = RandomDatabase()
    val petStore = PetStore(database)
    petStore.addPet(AddPetRequest("Fluffy"))
    // [INFO] - Adding pet with name Fluffy
    // [INFO] - Added pet with id -81731626
    // or
    // [WARNING] - There already is pet named Fluffy
    // or
    // [ERROR] - Failed to add pet with name Fluffy
}

class RandomDatabase : Database {
    override fun addPet(addPetRequest: AddPetRequest): Pet =
        when {
            Random.nextBoolean() ->
                Pet(1234, addPetRequest.name)
            Random.nextBoolean() ->
                throw InsertionConflictException()
            else -> throw Exception()
        }
}

class ConsoleLogger : Logger {
    override fun logInfo(message: String) {
        println("[INFO] - $message")
    }

    override fun logWarning(message: String) {
        println("[WARNING] - $message")
    }

    override fun logError(message: String) {
        println("[ERROR] - $message")
    }
}
