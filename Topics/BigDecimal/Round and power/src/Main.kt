import java.math.RoundingMode

fun main() {
    val power = readln().toInt()
    val mode = readln().toInt()
    val number = readln().toBigDecimal()

    println(number.setScale(mode, RoundingMode.FLOOR).pow(power))

}