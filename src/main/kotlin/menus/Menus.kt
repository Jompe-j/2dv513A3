package menus

import db.Queries
import java.lang.Exception
import java.util.*


class Menus {

    val reader = Scanner(System.`in`)
    val query = Queries()

    fun mainMenu() {
        var input = 0
        println("Make a choice")
        println("1, Search for users (list and specify)")
        println("2, Find the most common categories.")
        println("3, Find the category with the most stars")
        println("4, Find the user with friends that have given the largest number of stars")

        try {
            input = reader.nextInt()
            if (input > 6 || input < 1) throw Exception()
            selectionMainMenu(input)
        } catch (e: Exception) {
            println(e.message)
            mainMenu()
        }

    }

    private fun selectionMainMenu(input: Int) {
        when (input) {
            1 -> searchForInput()
            2 -> findCommonCat()
            3 -> findCatStar()
            4 -> findUsersWithFriends()
        }
    }

    private fun findCatStar() {
        orQuit(query.findCategoryWithMostReviews())
    }

    private fun findCommonCat() {
        orQuit(query.findMostCommonCategories())
    }

    private fun findUsersWithFriends() {
       orQuit(getSpecificUser(query.findUserWithFriendsGivenMostStars()))
    }

    private fun searchForInput() {
        try {
            println("Enter search string")
            val stringInput = readLine()!!
            var idList = query.findUsers(stringInput)
            orQuit(getSpecificUser(idList))
        } catch (e: Exception) {
            mainMenu()
        }
    }

    private fun orQuit(menuFunction: Unit) {
        menuFunction
        println("Press 0 to go to main menu")
        try {
            val choice = reader.nextInt()
            when (choice) {
                0 -> mainMenu()
            }
        } catch (e: Exception) {
            mainMenu()
        }

    }

    private fun getSpecificUser(idList: MutableList<Triple<String, Int, String>>) {
        var count = 0
        for (id in idList) {
            println("TmpId: $count   Name: ${id.first.padEnd(15)} Number of reviews: ${id.second}")
            count++
        }

        println("Select a TmpId to get Userdata")
        try {
            val choice = reader.nextInt()
            if (choice > count || choice < 0) {
                throw Exception()
            }
            query.getUser(idList[choice].third)
        } catch (e: Exception) {
        }
    }

}

