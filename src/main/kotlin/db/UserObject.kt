package db

import java.time.LocalDate

class UserObject {
    lateinit var user_id: String
    lateinit var name: String
    lateinit var yelping_since: String
    lateinit var date: java.sql.Date
    lateinit var friends: String
    var useful: Int = 0
    var funny: Int = 0
    var cool: Int = 0
    lateinit var friendsList: List<String>

    fun friendsToList() {
        this.friendsList = this.friends.split(",").map { it.trim() }
    }

    fun fixDate() {
        val tmpDate = LocalDate.parse(this.yelping_since)
        this.date = java.sql.Date.valueOf(tmpDate)

    }
}