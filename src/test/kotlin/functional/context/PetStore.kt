package functional.context

import org.junit.After
import org.junit.Test
import kotlin.test.assertEquals

class PetStoreTest {
    private val database = FakeDatabase()
    private val petStore = PetStore(database)
    private val logger = FakeLogger()

    @After
    fun tearDown() {
        database.clear()
        logger.clear()
    }

    @Test
    fun `should add pet`() {
        val pet = with(logger) {
            petStore.addPet(AddPetRequest("Fluffy"))
        }
        val expected = Pet(0, "Fluffy")
        assertEquals(expected, pet)
        assertEquals(expected, database.getPets().single())
    }

    @Test
    fun `should return null when database failing`() {
        database.startFailing()
        val pet = with(logger) {
            petStore.addPet(AddPetRequest("Fluffy"))
        }
        assertEquals(null, pet)
        assertEquals(emptyList<Pet>(), database.getPets())
    }

    @Test
    fun `should return null when conflict`() {
        database.addPet(AddPetRequest("Fluffy"))
        val pet = with(logger) {
            petStore.addPet(AddPetRequest("Fluffy"))
        }
        assertEquals(null, pet)
    }

    @Test
    fun `should log info when added pet`() {
        with(logger) {
            petStore.addPet(AddPetRequest("Fluffy"))
        }
        assertEquals(
            listOf(
                FakeLogger.Level.INFO to "Adding pet with name Fluffy",
                FakeLogger.Level.INFO to "Added pet with id 0",
            ),
            logger.getMessages()
        )
    }

    @Test
    fun `should log warning when adding conflict`() {
        database.addPet(AddPetRequest("Fluffy"))
        with(logger) {
            petStore.addPet(AddPetRequest("Fluffy"))
        }
        assertEquals(
            listOf(
                FakeLogger.Level.INFO to "Adding pet with name Fluffy",
                FakeLogger.Level.WARNING to "There already is pet named Fluffy",
            ),
            logger.getMessages()
        )
    }

    @Test
    fun `should log error when database error`() {
        database.startFailing()
        with(logger) {
            petStore.addPet(AddPetRequest("Fluffy"))
        }
        assertEquals(
            listOf(
                FakeLogger.Level.INFO to "Adding pet with name Fluffy",
                FakeLogger.Level.ERROR to "Failed to add pet with name Fluffy",
            ),
            logger.getMessages()
        )
    }
}

class FakeLogger : Logger {
    private val messages = mutableListOf<Pair<Level, String>>()

    fun clear() {
        messages.clear()
    }

    fun getMessages(): List<Pair<Level, String>> = messages.toList()

    override fun logInfo(message: String) {
        messages.add(Level.INFO to message)
    }

    override fun logWarning(message: String) {
        messages.add(Level.WARNING to message)
    }

    override fun logError(message: String) {
        messages.add(Level.ERROR to message)
    }

    enum class Level {
        INFO, WARNING, ERROR
    }
}

class FakeDatabase : Database {
    private val pets = mutableListOf<Pet>()
    private var shouldFail = false

    fun startFailing() {
        shouldFail = true
    }

    fun clear() {
        pets.clear()
        shouldFail = false
    }

    override fun addPet(addPetRequest: AddPetRequest): Pet {
        if (pets.any { it.name == addPetRequest.name }) throw InsertionConflictException()
        if (shouldFail) throw Exception()
        val pet = Pet(pets.size, addPetRequest.name)
        pets.add(pet)
        return pet
    }

    fun getPets(): List<Pet> = pets.toList()
}
