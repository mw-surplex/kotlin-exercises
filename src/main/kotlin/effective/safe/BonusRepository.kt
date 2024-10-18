package effective.safe

import effective.safe.BonusRepository.User

// This repo is incomplete and full of errors. Improve it
class BonusRepository(
    private val bonusesService: BonusesService
) {
    private val users: MutableSet<User> = mutableSetOf()
    private val bonuses: MutableMap<User, MutableList<String>> = mutableMapOf()

    fun addUser(user: User) {
        users += user
    }

    operator fun contains(user: User) = user in users

    fun changeUserSurname(userId: Int, newSurname: String?) {
        users.first { it.id == userId }.surname = newSurname
    }

    fun addBonus(user: User, bonus: String) {
        bonuses[user] = bonuses[user]?.toMutableList()?.apply { add(bonus) }
            ?: mutableListOf(bonus)
        bonusesService.update(bonuses)
    }

    fun removeBonus(user: User, bonus: String) {
        bonuses[user] = bonuses[user]?.toMutableList()?.apply { remove(bonus) }
            ?: mutableListOf()
        bonusesService.update(bonuses)
    }

    fun updateBonus(user: User, oldBonus: String, newBonus: String) {
        bonuses[user] = bonuses[user]?.toMutableList()?.apply {
            remove(oldBonus)
            add(newBonus)
        } ?: mutableListOf(newBonus)
        bonusesService.update(bonuses)
    }

    fun bonusesOf(user: User) = bonuses[user]!!

    data class User(
        var id: Int,
        var surname: String?,
        var name: String?
    )
}

fun main() {
    val bonusRepository = BonusRepository(
        bonusesService = PrintingBonusesService
    )
    val user1 = User(0, "Moskała", "Marcin")
    val user2 = User(1, "Kowalski", "Jan")
    bonusRepository.addUser(user1)
    bonusRepository.addUser(user2)
    // ...
}

interface BonusesService {
    fun update(bonuses: Map<User, List<String>>)
}

object PrintingBonusesService : BonusesService {
    override fun update(bonuses: Map<User, List<String>>) {
        print("Bonuses changed to $bonuses")
    }
}
