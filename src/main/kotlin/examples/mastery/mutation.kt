package examples.mastery

data class User(val name: String)

class UserRepository {
   private val storedUsers: MutableMap<Int, String> = mutableMapOf()

   fun loadAll() = storedUsers
  
   fun add(id: Int, name: String) {
       storedUsers[id] = name
   }
   //...
}

fun main() {
//    val repo = UserRepository()
//    val users = repo.loadAll()
//    users[1] = "ABC" // Should not be possible!
//    println(repo.loadAll()) // {1=ABC}
}
