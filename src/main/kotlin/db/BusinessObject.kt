package db

class BusinessObject() {

    lateinit var business_id: String
    lateinit var name: String
    lateinit var city: String
    var categories: String? = null

    lateinit var categoryList: List<String>

    fun categoriesToList() {
        if (categories != null){
            this.categoryList = this.categories!!.split(",").map { it.trim() }
        } else
        {
            this.categoryList = emptyList()
        }
    }
}