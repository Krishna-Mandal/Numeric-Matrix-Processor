import java.math.BigDecimal

fun main() {
    var in1 = BigDecimal(readln())
    var in2 = BigDecimal(readln())
    println(in1.setScale(in1.scale()) * in2)
}