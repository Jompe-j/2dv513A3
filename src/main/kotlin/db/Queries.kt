package db

class Queries {
    val db = DBHandler()

    fun findUsers(input: String): MutableList<Triple<String, Int, String>> {
        db.connect()
        var query =
            "select name, uid, count(review_id) as reviews from (select userdata.name, user_id as uid from userdata where userdata.name like ?) as userTable inner join userreview on userTable.uid = userreview.user_id group by uid, name order by  reviews, name desc limit 10"


        var pstmt = db.db.prepareStatement(query)
        pstmt.setString(1, "%$input%")
        var rs = pstmt.executeQuery()
        db.db.close()
        var idList = mutableListOf<Triple<String, Int, String>>()
        while (rs.next()) { // Name and reviews should be presented.
            var name = rs.getString("name")
            var count = rs.getInt("reviews")
            var uid = rs.getString("uid")
            idList.add(Triple(name, count, uid))
        }
        return idList
    }

    fun findMostCommonCategories() {
        db.connect()
        var query =
            "select count(category.business_id), category.category from business inner join category on business.business_id = category.business_id group by category order by count desc limit 10"

        var pstmt = db.db.prepareStatement(query)
        var rs = pstmt.executeQuery()
        db.db.close()

        while (rs.next()) {
            val category = rs.getString("category")
            val count = rs.getString("count")
            println("Category: ${category.padEnd(35)} Number of businesses with that category: $count")
        }
    }

    fun findUserWithFriendsGivenMostStars(): MutableList<Triple<String, Int, String>> {
        db.connect()
        var query = "select userdata.name, us.user_id, totalScore from ( select user_id, sum(score) as totalScore from ( select ur.user_id as friendWithScore, sum(stars) as score from userreview as ur inner join review as rev on ur.review_id = rev.review_id group by ur.user_id ) as countTable inner join friends on friends.friend_id = countTable.friendWithScore group by user_id order by totalScore desc limit 10 ) as us inner join userdata on us.user_id = userdata.user_id"
        var pstmt = db.db.prepareStatement(query)
        var rs = pstmt.executeQuery()
        db.db.close()
var metadata = rs.metaData
        println(metadata.getColumnName(1))
        println(metadata.getColumnName(2))
        println(metadata.getColumnName(3))
            var idList = mutableListOf<Triple<String, Int, String>>()
        while (rs.next()) { // Name and reviews should be presented.
            var name = rs.getString("name")
            var count = rs.getInt("totalScore")
            var uid = rs.getString("user_id")
            idList.add(Triple(name, count, uid))
        }
        return idList
    }

    fun findCategoryWithMostReviews() {
        db.connect()
        var query =
            "select count(rvid), category from (select name, bc.business_id, bbc.review_id as rvId, bc.category from (select category.business_id, category.category, name from business inner join category on business.business_id = category.business_id) as bc inner join businessreview as bbc on bc.business_id = bbc.business_id) as rbbc group by category order by count desc limit 10"

        var pstmt = db.db.prepareStatement(query)
        var rs = pstmt.executeQuery()
        db.db.close()

        while (rs.next()) {
            val category = rs.getString("category")
            val count = rs.getString("count")
            println("Category: ${category.padEnd(35)} Number of reviews concerning this category: $count")
        }
    }

    fun getUser(third: String) {
        db.connect()
        var query = "select name, reviews, totalstars, avg(totalstars/reviews) from (select selrev.user_id, count(review.review_id) as reviews, sum(stars) as totalstars from (select * from userreview where user_id = ?) as selrev inner join review on selrev.review_id = review.review_id group by selrev.user_id) as tmpTable inner join userdata on userdata.user_id = tmpTable.user_id group by name, reviews, totalstars"

        var pstmt = db.db.prepareStatement(query)
        pstmt.setString(1, third)
        var rs = pstmt.executeQuery()
        db.db.close()

        while (rs.next()) {
          val name = rs.getString("name")
          val reviews = rs.getString("reviews")
          val totalStars = rs.getString("totalstars")
          val avgStars = rs.getString("avg")
            println("Name: ${name.padEnd(25)} Number of reviews: ${reviews.padEnd(10)} Total stars:\t ${totalStars.padEnd(5)} Average Stars: ${avgStars.subSequence(0, 3)}")
        }
    }


}