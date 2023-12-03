package util

typealias Matrix<T> = MutableList<MutableList<T>>

fun <T> matrixFromString(input: String, emptyDefault : T, function : (Char) -> T): Matrix<T> {
    val lines = input.split("\n")
    val res = emptyMatrixOf(lines.size, lines[0].length, emptyDefault)

    lines.forEachIndexed { y, line ->
        line.forEachIndexed { x, case ->
            run {
                res[y][x] = function(lines[y][x])
            }
        }
    }
    return res
}


fun <E> Matrix<E>.forEachPoint(point: (Point) -> Unit) {
    this.forEachIndexed { y, line ->
        line.indices.forEach { x ->
            point(Point(x, y))
        }
    }
}

fun <T> matrixOf(vararg rows: MutableList<T>): Matrix<T> = MutableList(rows.size) { i -> rows[i] }
fun <T> matrixOf(rows: MutableList<MutableList<T>>): Matrix<T> = MutableList(rows.size) { i -> rows[i] }
fun <T> Matrix<T>.toString(): String = this.joinToString("\n") { it.joinToString(", ") }

operator fun <E> Matrix<E>.set(pos : Point, value : E) {
    this[pos.y][pos.x] = value
}

operator fun <T> Matrix<T>.get(pos : Point)= this[pos.y][pos.x]

fun <T> Matrix<T>.rotateRight() : Unit {
    var temp = matrixOf(this)
    this.removeAll { true }

    repeat (temp[0].size) {
        this.add(MutableList<T> (temp.size) { temp[0][0] } )
    }

    for (y in 0 until temp.size) {
        for (x in 0 until temp[0].size) {
            this[x][temp.size - 1 - y] = temp[y][x]
        }
    }
}


fun <T> emptyMatrixOf(rows: Int, columns: Int, default: T) = MutableList(rows) { MutableList(columns) { default } }

fun <T> Matrix<T>.clone() : Matrix<T> {
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


fun Matrix<Char>.addFirstLine(element : Char?) {
    this.add(0, MutableList<Char>(this[0].size) { element?.let { element } ?: ' ' })
}

fun showMap(map : List<List<Any>>) {
    showMap(map, 0, map.size)
}

fun showMap(map : List<List<Any>>, from : Int, to : Int) {
    print( "   ")

    for (i in 0 until map[0].size) {
        print("%3d".format(i))
    }

    println()

    for ((index, line) in map.drop(from).take(to - from).withIndex()) {
        print("%3d".format(index))
        for (cell in line) {
            print("%3s".format(cell))
        }
        println()
    }
}