package processor

import kotlin.system.exitProcess

fun main() {

    val (row1, col1) = readln().split(" ").map { it.toInt() }
    val matrix1 = Matrix(row = row1, col = col1)
    matrix1.fillMatrix()

    val (row2, col2) = readln().split(" ").map { it.toInt() }
    val matrix2 = Matrix(row = row2, col = col2)
    matrix2.fillMatrix()

    val matrix3 = matrix1.addMatrix(matrix2.matrix, inRow = row2, inCol = col2)
    Matrix.printMatrix(matrix3, row1, col1)
}

class Matrix(private val row: Int, private val col: Int) {

    var matrix : Array<Array<Int>> = emptyArray()

    companion object {
        fun printMatrix(inMatrix : Array<Array<Int>>, inRow : Int, inCol : Int) {
            if (inMatrix.isNotEmpty()) {
                for (row in 0 until inRow) {
                    for (col in 0 until inCol) {
                        print(inMatrix[row][col])
                        if (col != inCol - 1) {
                            print(" ")
                        }
                    }
                    println()
                }
            }
        }

    }

    fun printMatrix() {
        Companion.printMatrix(matrix, row, col)
    }

    fun fillMatrix() {
        matrix = Array(row) {
            readln().split(" ").map { it.toInt() }.toTypedArray()
        }
    }

    fun addMatrix(inMatrix : Array<Array<Int>>, inRow : Int, inCol : Int): Array<Array<Int>> {
        val newMatrix : Array<Array<Int>> = Array(inRow) { Array(inCol) {0} }
        if (row != inRow && col != inCol) {
            println("ERROR")
            exitProcess(0)
        } else {
            for (row1 in 0 until row) {
                for (col1 in 0 until col) {
                    newMatrix[row1][col1] = matrix[row1][col1] + inMatrix[row1][col1]
                }
            }
        }

        return newMatrix
    }

}
