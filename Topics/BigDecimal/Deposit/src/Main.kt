import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
    val startingAmount = BigDecimal(readln())
    val interest = readln().toBigDecimal()
    val numOfYears = readln().toInt()

    val finalAmount = startingAmount * ((BigDecimal.ONE + interest.setScale(2 + interest.scale()) / 100.toBigDecimal()).pow(numOfYears))

    println("Amount of money in the account: ${finalAmount.setScale(2, RoundingMode.CEILING)}")
}