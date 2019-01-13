import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.BufferedReader
import java.io.FileReader

fun deserializeBusiness() {

    val mapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    var br = BufferedReader(FileReader("c:/users/johna/kod/2dv513/data/yelp/business.json"))
    val db = DBHandler()

    var line: String? = br.readLine()
    var businessCollection = mutableListOf<BusinessObject>()
    while (line != null) {
        val businessObject = mapper.readValue<BusinessObject>(line)
        businessObject.categoriesToList()
        businessCollection.add(businessObject)
        line = br.readLine()

    }
    db.connect()
//    db.populateBusiness()
//    db.populateCategory()
}

fun deserializeUser() {
    val mapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    var br = BufferedReader(FileReader("c:/users/johna/kod/2dv513/data/yelp/user.json"))
    val db = DBHandler()
    var count = 0
    println("deserialize user")
    var line: String? = br.readLine()
    var userCollection = mutableListOf<UserObject>()
    while (line != null) {
        userCollection.add(getUser(mapper, line))
        // system cannot handle to many objects at once.
        if (count > 100000){
/*            db.populateUser(userCollection)*/
            db.populateFriends(userCollection)
            userCollection.clear()
            count = 0
        }

        line = br.readLine()
        if (line == null && userCollection.isNotEmpty()) {
/*            db.populateUser(userCollection)*/
            db.populateFriends(userCollection)
            userCollection.clear()
            count = 0
        }
        count++
    }
    println("Done")
}

private fun getUser(mapper: ObjectMapper, line: String): UserObject {
    val user = mapper.readValue<UserObject>(line)
    user.friendsToList()
    user.fixDate()
    return user
}

fun deserializeReview() {
    val mapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    var br = BufferedReader(FileReader("c:/users/johna/kod/2dv513/data/yelp/review.json"))
    val db = DBHandler()
    var count = 0
    println("deserialize review")
    var line: String? = br.readLine()
    var reviewCollection = mutableListOf<ReviewObject>()
    while (line != null) {
        reviewCollection.add(getReview(mapper, line))
        // system cannot handle to many objects at once.
        if (count > 100000){
/*            db.populateUser(userCollection)*/
            db.populateReview(reviewCollection)
            reviewCollection.clear()
            count = 0
        }

        line = br.readLine()
        if (line == null && reviewCollection.isNotEmpty()) {
/*            db.populateUser(userCollection)*/
            db.populateReview(reviewCollection)
            reviewCollection.clear()
            count = 0
        }
        count++
    }
    println("Done")
}
private fun getReview(mapper: ObjectMapper, line: String): ReviewObject {
    val reviewObject = mapper.readValue<ReviewObject>(line)
    reviewObject.fixDateString()
    return reviewObject
}