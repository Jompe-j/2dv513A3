package db

import java.time.LocalDate

class ReviewObject() {
    lateinit var review_id: String
    lateinit var user_id: String
    lateinit var business_id: String
    var stars: Int = 0
    lateinit var text: String
    lateinit var date: String
    lateinit var realDate: java.sql.Date
    var useful: Int = 0
    var funny: Int = 0
    var cool: Int = 0

    fun fixDateString() {
        val tmpDate = LocalDate.parse(date)
        this.realDate = java.sql.Date.valueOf(tmpDate)
    }
}