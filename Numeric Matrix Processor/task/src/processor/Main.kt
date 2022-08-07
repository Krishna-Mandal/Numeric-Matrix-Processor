package processor

import kotlin.math.pow
import kotlin.system.exitProcess

fun printChoices() {
    println("""
        1. Add matrices
        2. Multiply matrix by a constant
        3. Multiply matrices
        4. Transpose matrix
        5. Calculate a determinant
        6. Inverse matrix
        0. Exit
    """.trimIndent())
}

fun printTransposeChoices() {
    println("""
        1. Main diagonal
        2. Side diagonal
        3. Vertical line
        4. Horizontal line
    """.trimIndent())
}

fun handleChoices(choice: Int) {
    when (choice) {
        0 -> exitProcess(0)
        1 -> addMatrices()
        2 -> multiplyConstant()
        3 -> multiplyMatrices()
        4 -> {
            printTransposeChoices()
            handleTranspose()
        }
        5 -> calculateDeterminant()
        6 -> inverseMatrix()
    }
}

private fun inverseMatrix() {
    print("Enter matrix size: ")
    val (rows, cols) = readln().split(" ").map { it.toInt() }
    println("Enter matrix:")
    val matrix = Matrix(rows, cols)
    matrix.fillMatrix()

    println("The result is:")

    if (matrix.determinant() == 0.0) {
        println("This matrix doesn't have an inverse.")
        return
    }

    Matrix.printMatrix(matrix.inverse(), rows, cols)
}

fun calculateDeterminant() {
    println("Enter matrix size:")
    val (row, col) = readln().split(" ").map { it.toInt() }
    val matrix = Matrix(row = row, col = col)
    println("Enter matrix:")
    matrix.fillMatrix()
    
    println("The result is:")
    println(matrix.determinant())
}

fun handleTranspose() {
    val transposeChoice = readln().toInt()
    println("Enter matrix size:")
    val (row, col) = readln().split(" ").map { it.toInt() }
    val matrix = Matrix(row = row, col = col)
    println("Enter matrix:")
    matrix.fillMatrix()
    val transposedMatrix =  when(transposeChoice) {
        1 -> matrix.mainTranspose()
        2 -> matrix.sideTranspose()
        3 -> matrix.verticalTranspose()
        4 -> matrix.horizontalTranspose()
        else -> {
            matrix.matrix
        }
    }
    Matrix.printMatrix(inMatrix = transposedMatrix, inRow = row, inCol = col)
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

class Matrix(private var row: Int, private var col: Int) {

    var matrix : MutableList<MutableList<Double>> = MutableList(row) { MutableList(col) {0.0} }

    companion object {
        fun printMatrix(inMatrix : MutableList<MutableList<Double>>, inRow : Int, inCol : Int) {
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

        private fun determinant(inMatrix: Matrix): Double {
            if (inMatrix.row == 2 && inMatrix.col == 2) {
                return inMatrix.matrix[0][0] * inMatrix.matrix[1][1] - inMatrix.matrix[0][1] * inMatrix.matrix[1][0]
            } else if (inMatrix.row == 1 && inMatrix.col == 1) {
                return inMatrix.matrix[0][0]
            }

            val minors = mutableListOf<Matrix>()

            for (i in 0 until inMatrix.col) {
                minors.add(getMinorAtIndex(copy(inMatrix), 0, i))
            }

            return minors.mapIndexed { index, it -> inMatrix.matrix[0][index] * it.determinant() * (-1.0).pow(index) }.sum()
        }
        
        private fun getMinorAtIndex(inMatrix: Matrix, row: Int, column: Int): Matrix {
            inMatrix.matrix.removeAt(row)
            inMatrix.matrix.forEach { it.removeAt(column) }
            inMatrix.row -= 1
            inMatrix.col -= 1

            return inMatrix
        }

        private fun copy(matrix: Matrix): Matrix {
            val newMatrix = Matrix(matrix.row, matrix.col)

            for (i in 0 until matrix.row) {
                for (j in 0 until matrix.col) {
                    newMatrix.matrix[i][j] = matrix.matrix[i][j]
                }
            }

            return newMatrix
        }

    }

    fun determinant(): Double {
        return Companion.determinant(this)
    }

    fun printMatrix() {
        Companion.printMatrix(matrix, row, col)
    }

    fun fillMatrix() {
        matrix = MutableList(row) {
            readln().split(" ").map { it.toDouble() }.toMutableList()
        }
    }

    fun addMatrix(inMatrix : MutableList<MutableList<Double>>, inRow : Int, inCol : Int): MutableList<MutableList<Double>> {
        val newMatrix : MutableList<MutableList<Double>> = MutableList(inRow) { MutableList(inCol) {0.0} }
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

    fun multiplyByConstant(mul: Double): MutableList<MutableList<Double>> {
        val newMatrix : MutableList<MutableList<Double>> = MutableList(row) { MutableList(col) {0.0} }
        for (row1 in 0 until row) {
            for (col1 in 0 until col) {
                newMatrix[row1][col1] = matrix[row1][col1] * mul
            }
        }

        return newMatrix
    }

    fun multiplyMatrix(inMatrix: MutableList<MutableList<Double>>, inRow: Int, inCol: Int): MutableList<MutableList<Double>> {
        val newMatrix : MutableList<MutableList<Double>> = MutableList(row) { MutableList(inCol) { 0.0 } }
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

    fun mainTranspose() : MutableList<MutableList<Double>> {
        val transposedMatrix = MutableList(col) { MutableList(row) { 0.0 } }

        for (i in transposedMatrix.indices) {
            for (j in transposedMatrix[i].indices) {
                transposedMatrix[i][j] = matrix[j][i]
            }
        }

        return transposedMatrix
    }

    fun sideTranspose(): MutableList<MutableList<Double>> {
        val transposedMatrix = MutableList(col) { MutableList(row) { 0.0 } }

        for (i in transposedMatrix.indices) {
            for (j in transposedMatrix[i].indices) {
                transposedMatrix[i][j] = matrix[col - j - 1][row - i - 1]
            }
        }

        return transposedMatrix
    }

    fun verticalTranspose(): MutableList<MutableList<Double>> {
        val transposedMatrix = MutableList(row) { MutableList(col) { 0.0 } }

        for (i in transposedMatrix.indices) {
            for (j in transposedMatrix[i].indices) {
                transposedMatrix[i][j] = matrix[i][col - j - 1]
            }
        }

        return transposedMatrix
    }

    fun horizontalTranspose(): MutableList<MutableList<Double>> {
        val transposedMatrix = MutableList(row) { MutableList(col) { 0.0 } }

        for (i in transposedMatrix.indices) {
            for (j in transposedMatrix[i].indices) {
                transposedMatrix[i][j] = matrix[row - i - 1][j]
            }
        }

        return transposedMatrix
    }

    fun inverse(): MutableList<MutableList<Double>> {
        val adjoint = Matrix(row, col)

        for (i in 0 until row) {
            for (j in 0 until col) {
                adjoint.matrix[i][j] = getMinorAtIndex(copy(this), i, j).determinant() * (-1.0).pow(i + j)
            }
        }
        adjoint.matrix = adjoint.mainTranspose()
        return adjoint.multiplyByConstant(1 / determinant())
    }

}
