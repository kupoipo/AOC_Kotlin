package util

import java.lang.Exception

typealias Matrix<T> = MutableList<MutableList<T>>

fun <T> matrixFromString(input: String, emptyDefault: T, function: (Char) -> T): Matrix<T> {
    return matrixFromStringIndexed(input, emptyDefault)  { char, _, _ -> function(char) }
}

val <E> Matrix<E>.nbColumns: Int
    get() {
        return this.first().size
    }

fun <T> matrixFromStringIndexed(input: String, emptyDefault: T, function: (Char, Int, Int) -> T): Matrix<T> {
    val lines = input.split("\n")
    val res = emptyMatrixOf(lines.size, lines.maxOf { it.length }, emptyDefault)

    lines.forEachIndexed { y, line ->
        line.forEachIndexed { x, case ->
            run {
                try {
                    res[y][x] = function(lines[y][x], y, x)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
    return res
}

fun <E> Matrix<E>.numberOf(el : E) = this.sumOf { it.count { it == el} }

fun <E> Matrix<E>.pointOfFirst(function: (E) -> Boolean): Point {
    for (lig in this.indices) {
        for (col in this[lig].indices) {
            if (function(this[lig][col])) {
                return Point(col, lig)
            }
        }
    }

    return Point(-1, -1)
}

fun <E> Matrix<E>.forEachPoint(point: (Point) -> Unit) {
    this.forEachIndexed { y, line ->
        line.indices.forEach { x ->
            point(Point(x, y))
        }
    }
}

fun <E> Matrix<E>.points(): List<Point> {
    var res = mutableListOf<Point>()
    this.forEachIndexed { y, line ->
        line.indices.forEach { x ->
            res.add(Point(x, y))
        }
    }
    return res
}

fun <T> matrixOf(vararg rows: MutableList<T>): Matrix<T> = MutableList(rows.size) { i -> rows[i] }
fun <T> matrixOf(rows: MutableList<MutableList<T>>): Matrix<T> = MutableList(rows.size) { i -> rows[i] }
fun <T> Matrix<T>.toString(): String = this.joinToString("\n") { it.joinToString(", ") }

operator fun <E> Matrix<E>.set(pos: Point, value: E) {
    this[pos.y][pos.x] = value
}

operator fun <E> MutableList<E>.set(x: Long, value: E) {
    this[x.toInt()] = value
}

operator fun <E> MutableList<E>.get(y: Long): E {
    return this[y.toInt()]
}



operator fun <T> Matrix<T>.get(pos: Point) = this[pos.y][pos.x]

fun <T> Matrix<T>.rotateLeft(): Unit {
    repeat(3) {
        rotateRight()
    }
}


fun <T> Matrix<T>.rotateRight(): Unit {
    var temp = matrixOf(this)
    this.removeAll { true }

    repeat(temp[0].size) {
        this.add(MutableList<T>(temp.size) { temp[0][0] })
    }

    for (y in 0 until temp.size) {
        for (x in 0 until temp[0].size) {
            this[x][temp.size - 1 - y] = temp[y][x]
        }
    }
}


fun <T> MutableList<T>.swap(i1: Int, i2: Int) {
    var index1 = i1
    var index2 = i2

    if (i1 > i2) {
        index1 = i2
        index2 = i1
    }

    val o1 = this.removeAt(index1)
    val o2 = this.removeAt(index2 - 1)

    this.add(index1, o2)
    this.add(index2, o1)
}

fun <T> emptyMatrixOf(rows: Int, columns: Int, default: T) = MutableList(rows) { MutableList(columns) { default } }
fun <T> emptyMatrixOf(rows: Long, columns: Long, default: T) = emptyMatrixOf(rows.toInt(), columns.toInt(), default)

fun <T> Matrix<T>.clone(): Matrix<T> {
    var res = emptyMatrixOf(this.size, this[0].size, this[0][0])

    res.forEachIndexed { y, ligne ->
        run {
            ligne.forEachIndexed { x, case ->
                run {
                    res[y][x] = this[y][x]
                }
            }
        }
    }

    return res
}


fun <T> Matrix<T>.flipY() {
    for (i in 0 until this.size / 2) { // Itérer jusqu'à la moitié des lignes
        for (j in this[i].indices) { // Itérer sur toutes les colonnes
            val temp = this[i][j]
            this[i][j] = this[this.size - 1 - i][j] // Échange avec la ligne opposée
            this[this.size - 1 - i][j] = temp
        }
    }
}


fun <T> Matrix<T>.flipX() {
    for (i in 0 until this.size) {
        for (j in 0 until this[i].size / 2) { // Itérer jusqu'à la moitié de la ligne
            val temp = this[i][j]
            this[i][j] = this[i][this[i].lastIndex - j]
            this[i][this[i].lastIndex - j] = temp
        }
    }
}


fun Matrix<Char>.addFirstLine(element: Char?) {
    this.add(0, MutableList(this[0].size) { element?.let { element } ?: ' ' })
}

fun <T> showMap(map: List<List<T>>, nbChar: Int = 4, transformation: (T) -> (String) = { it.toString() }) {
    showMap(map, 0, map.size, nbChar, transformation)
}
fun <T> showMap(map: List<List<T>>, from: Int, to: Int, nbChar: Int, transformation: (T) -> (String)) {

    print("%${nbChar}s".format(""))

    for (i in 0 until map[0].size) {
        print("%${nbChar}d".format(i))
    }

    println()

    for ((index, line) in map.drop(from).take(to - from).withIndex()) {
        print("%4d".format(index))
        for (cell in line) {
            print("%${nbChar}s".format(transformation(cell)))
        }
        println()
    }
}

fun <T> Matrix<T>.withoutSides() : Matrix<T> {
    val res : Matrix<T> = emptyMatrixOf(this.size - 2, this[0].size - 2, this[0][0])

    for (lig in 1..this.size-2) {
        for (col in 1..this[0].size-2) {
            res[lig-1][col-1] = this[lig][col]
        }
    }

    return res
}

fun <T> Matrix<T>.corners(): List<Point> =
    listOf(Point(0, 0), Point(0, this.size - 1), Point(this[0].size - 1, 0), Point(this[0].size - 1, this.size - 1))

fun <E> Matrix<E>.tile(nbRow: Int, nbCol: Int, default: E): Matrix<E> {
    val res = emptyMatrixOf(this.size * nbRow, this[0].size * nbCol, default)

    this.points().forEach { p ->
        repeat(nbRow) { r ->
            val row = p.y + r * this.size
            repeat(nbCol) { c ->
                val col = p.x + c * this[p.y].size

                res[row][col] = this[p]
            }
        }
    }

    return res
}


fun <E> Matrix<E>.setCenter(el: E) {
    this[this.size / 2][this[0].size / 2] = el
}