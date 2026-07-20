package Projects.OrderSystem.Domain

data class Product(
    val id:String,
    val name:String,
    val price:Double,
    val minQty:Int,
    val maxQty:Int,
){
    init {
        require(minQty>= 1){
            "minQty must be >=1"
        }
        require(maxQty>= minQty){
            "maxQty must be >= minQty"
        }
        require(price>=0){
            "Price must be >=0"
        }
    }

}