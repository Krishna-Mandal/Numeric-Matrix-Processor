import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
    val bigList = MutableList(3) { readln().toBigDecimal() }
    val sum = bigList.sumOf { it }
    println(sum.setScale(0, RoundingMode.DOWN) / 3.toBigDecimal())
}