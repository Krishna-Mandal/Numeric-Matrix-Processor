package processor

import kotlin.system.exitProcess

fun printChoices() {
    println("""
        1. Add matrices
        2. Multiply matrix by a constant
        3. Multiply matrices
        0. Exit
    """.trimIndent())
}

fun handleChoices(choice: Int) {
    when (choice) {
        0 -> exitProcess(0)
        1 -> addMatrices()
        2 -> multiplyConstant()
        3 -> multiplyMatrices()
    }
}

fun multiplyMatrices() {
    println("Enter size of first matrix:")
    val (row1, col1) = readln().split(" ").map { it.toInt() }
    val matrix1 = Matrix(row = row1, col = col1)
    println("Enter first matrix:")
    matrix1.fillMatrix()

    println("Enter size of second matrix:")
    val (row2, col2) = readln().split(" ").map { it.toInt() }
    val matrix2 = Matrix(row = row2, col = col2)
    println("Enter second matrix:")
    matrix2.fillMatrix()

    val matrix = matrix1.multiplyMatrix(inMatrix = matrix2.matrix, inRow = row2, inCol = col2)
    println("The result is:")
    Matrix.printMatrix(matrix, row1, col2)
}

fun multiplyConstant() {
    println("Enter size of matrix:")
    val (row1, col1) = readln().split(" ").map { it.toInt() }
    val matrix1 = Matrix(row = row1, col = col1)
    println("Enter matrix:")
    matrix1.fillMatrix()

    println("Enter constant:")
    val constant = readln().toDouble()

    val matrix3 = matrix1.multiplyByConstant(constant)
    println("The result is:")
    Matrix.printMatrix(matrix3, row1, col1)
}

fun addMatrices() {
    val (row1, col1) = readln().split(" ").map { it.toInt() }
    val matrix1 = Matrix(row = row1, col = col1)
    matrix1.fillMatrix()

    val (row2, col2) = readln().split(" ").map { it.toInt() }
    val matrix2 = Matrix(row = row2, col = col2)
    matrix2.fillMatrix()
    
    val matrix = matrix1.addMatrix(inMatrix = matrix2.matrix, inRow = row2, inCol = col2)
    Matrix.printMatrix(matrix, row2, col2)
}

fun main() {

    printChoices()
    while (true) {
        val choice = readln().toInt()
        handleChoices(choice)
    }

}

class Matrix(private val row: Int, private val col: Int) {

    var matrix : Array<Array<Double>> = emptyArray()

    companion object {
        fun printMatrix(inMatrix : Array<Array<Double>>, inRow : Int, inCol : Int) {
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
            readln().split(" ").map { it.toDouble() }.toTypedArray()
        }
    }

    fun addMatrix(inMatrix : Array<Array<Double>>, inRow : Int, inCol : Int): Array<Array<Double>> {
        val newMatrix : Array<Array<Double>> = Array(inRow) { Array(inCol) {0.0} }
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

    fun multiplyByConstant(mul: Double): Array<Array<Double>> {
        val newMatrix : Array<Array<Double>> = Array(row) { Array(col) {0.0} }
        for (row1 in 0 until row) {
            for (col1 in 0 until col) {
                newMatrix[row1][col1] = matrix[row1][col1] * mul
            }
        }

        return newMatrix
    }

    fun multiplyMatrix(inMatrix: Array<Array<Double>>, inRow: Int, inCol: Int): Array<Array<Double>> {
        val newMatrix : Array<Array<Double>> = Array(row) { Array(inCol) { 0.0 } }
        if (this.col != inRow) {
            println("The operation cannot be performed.")
        } else {
            for(i in newMatrix.indices){
                for(j in newMatrix[i].indices){
                    for(k in inMatrix.indices)
                        newMatrix[i][j] = newMatrix[i][j] + (matrix[i][k] * inMatrix[k][j])
                }
            }
        }

        return newMatrix
    }
}
