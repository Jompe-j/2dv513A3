import java.sql.*

class DBHandler {
    private val url = DBSettings().url
    private val username = DBSettings().userName
    private val password = DBSettings().password
    lateinit var db: Connection

    fun connect() {
        try {
            this.db = DriverManager.getConnection(url, username, password)
            db?.autoCommit = false
            println("Connected")
        } catch (error: SQLException) {
            println("connection failed")
            println(error.message)
        }
    }

    fun populateCategory(objectList: MutableList<BusinessObject>) {

        var query = "INSERT INTO category (business_id, category) VALUES (?, ?)"

        var pstmt = this.db?.prepareStatement(query)
        for (business in objectList) {
            for (category in business.categoryList) {
                pstmt?.setString(1, business.business_id)
                pstmt?.setString(2, category)
                pstmt?.addBatch()
            }
        }
        pstmt?.executeBatch()
        db?.commit()
        db?.close()

        if (db!!.isClosed) {
            println("Insert done")
        }
    }

    fun populateBusiness(objectList: MutableList<BusinessObject>) {
        var query = "INSERT INTO business (business_id, name, city) VALUES (?, ?, ?)"

        var pstmt = this.db?.prepareStatement(query)
        for (business in objectList) {
            pstmt?.setString(1, business.business_id)
            pstmt?.setString(2, business.name)
            pstmt?.setString(3, business.city)
            pstmt?.addBatch()
        }
        pstmt?.executeBatch()
        db?.commit()
        db?.close()

        if (db!!.isClosed) {
            println("Insert done")
        }
    }

    fun populateUser(objectList: MutableList<UserObject>) {
        connect()
        var query = "INSERT INTO userdata (user_id, name, date, useful, cool, funny) VALUES (?, ?, ?, ?, ?, ?)"

        var pstmt = this.db?.prepareStatement(query)
        for (user in objectList) {
            pstmt?.setString(1, user.user_id)
            pstmt?.setString(2, user.name)
            pstmt?.setDate(3, user.date)
            pstmt?.setInt(4, user.useful)
            pstmt?.setInt(5, user.cool)
            pstmt?.setInt(6, user.funny)
            pstmt?.addBatch()
        }
        pstmt?.executeBatch()
        db?.commit()
        db?.close()

        if (db!!.isClosed) {
            println("Insert done")
        }
    }

    fun populateFriends(objectList: MutableList<UserObject>) {
        connect()
        var query = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)"

        var pstmt = this.db?.prepareStatement(query)
        for (user in objectList) {
            for (friend in user.friendsList) {
                pstmt?.setString(1, user.user_id)
                pstmt?.setString(2, friend)
                pstmt?.addBatch()
            }

        }
        pstmt?.executeBatch()
        db?.commit()
        db?.close()

        if (db!!.isClosed) {
            println("Insert done")
        }
    }

    fun populateReview(objectList: MutableList<ReviewObject>) {
        connect()
        var query = "INSERT INTO review (review_id, business_id, user_id, created, stars, text, useful, cool, funny) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"


        var pstmt = this.db?.prepareStatement(query)
        for (review in objectList) {
            pstmt?.setString(1, review.review_id)
            pstmt?.setString(2, review.business_id)
            pstmt?.setString(3, review.user_id)
            pstmt?.setDate(4, review.realDate)
            pstmt?.setInt(5, review.stars)
            pstmt?.setInt(6, review.useful)
            pstmt?.setInt(7, review.stars)
            pstmt?.setInt(8, review.cool)
            pstmt?.setInt(9, review.funny)
            pstmt?.addBatch()
        }

        pstmt?.executeBatch()
        db?.commit()
        db?.close()

        if (db!!.isClosed) {
            println("Insert review done")
        }
    }
}
