import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.BufferedReader
import java.io.FileReader

fun populate() {
    readFile()
}

fun readFile(){
    val mapper = mapperSetup()
    var br = readerSetup()
    var line: String? = br.readLine()
    var businessCollection = mutableListOf<BusinessObject>()
    var counter = 0
    while (line != null) {
        if (counter < 30) {
            val businessObject = toJson(line, mapper)
            businessCollection.add(businessObject)

        }
        line = br.readLine()
        counter++
    }
    println(counter)
   }

private fun readerSetup(): BufferedReader {
    return BufferedReader(FileReader("c:/users/johna/kod/2dv513/data/yelp/business.json"))
}

private fun mapperSetup(): ObjectMapper {
    val mapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    return mapper
}

fun toJson(line: String, mapper: ObjectMapper): BusinessObject {
    return mapper.readValue<BusinessObject>(line)
}

class BusinessObject(val business_id: String, val name: String, neighborhood: String, val address: String,
                     val city: String, val postal_code: String, val latitude: Float, val longitude: Float,
                     val stars: Float, val review_count: Float, val categories: String)

