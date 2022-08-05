package processor

import java.util.Arrays

fun main() {

    val (row1, col1) = readln().split(" ").map { it.toInt() }

    val mat1 = Array(row1) {
            readln().split(" ").map { it.toInt() }
    }

    val (row2, col2) = readln().split(" ").map { it.toInt() }

    val mat2 = Array(row2) {
            readln().split(" ").map { it.toInt() }
    }

    if (row1 != row2 && col1 != col2) {
        println("ERROR")
    } else {
        for (row in 0 until row1) {
            for (col in 0 until col1) {
                print(mat1[row][col] + mat2[row][col])
                if (col != col1 - 1) {
                    print(" ")
                }
            }
            println()
        }
    }
}
