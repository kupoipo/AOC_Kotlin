package _2022.d23
import util.Point
import util.matrixOf
import util.readInput

import kotlin.system.measureTimeMillis

const val DECALAGE = 50
const val SIZE = 350

data class Elfe(var position : Point, var nextPos : Point? = null) {
    var order = mutableListOf(::canMoveNorth, ::canMoveSouth, ::canMoveWest, ::canMoveEast)

    override fun toString(): String = "En $position - va en : $nextPos"

    fun canMoveNorth() : Boolean {
        if ((position.x - 1.. position.x +1).sumBy { if (map[position.y -1][it] == '#') 1 else 0 } == 0) {
            nextPos = (Point(y = position.y - 1, x = position.x))
            return true
        }
        return false
    }

    fun canMoveSouth() : Boolean {
        if ((position.x - 1.. position.x +1).sumBy { if (map[position.y + 1][it] == '#') 1 else 0 } == 0) {
            nextPos = (Point(y = position.y + 1, x = position.x))
            return true
        }
        return false
    }

    fun canMoveWest() : Boolean {
        if ((position.y - 1.. position.y + 1).sumBy { if (map[it][position.x - 1] == '#') 1 else 0 } == 0) {
            nextPos = (Point(y = position.y, x = position.x - 1))
            return true
        }
        return false
    }

    fun canMoveEast() : Boolean {
        if ((position.y - 1.. position.y + 1).sumBy { if (map[it][position.x + 1] == '#') 1 else 0 } == 0) {
            nextPos = (Point(y = position.y, x = position.x + 1))
            return true
        }
        return false
    }

    fun updateOrder() {
        order.add(order.removeFirst())
    }
}

val elfes = mutableSetOf<Elfe>()
val map = matrixOf(MutableList<MutableList<Char>>(SIZE) { MutableList<Char>(SIZE) { '.'} } )

fun part1(content : List<String>) : String {
    for (line in content.withIndex()) {
        for (case in line.value.withIndex()) {
            if (case.value == '#') {
                elfes.add(Elfe(Point(y = DECALAGE + line.index, x = DECALAGE + case.index)))
                map[DECALAGE + line.index][DECALAGE + case.index] = '#'
            }
        }
    }

    var cpt = 0
    var moved = true

    while (moved) {
        cpt++
        for (elfe in elfes) {
            if (!noElfesAround(elfe.position)) {
                for (i in elfe.order.indices)
                    if (elfe.order[i]()) break
            }

            elfe.updateOrder()
        }

        for (elfe1 in elfes) {
            if (elfe1.nextPos == null) continue

            var toNull = false

            for (elfe2 in elfes) {
                if (elfe1 != elfe2 && elfe1.nextPos == elfe2.nextPos) {
                    elfe2.nextPos = null
                    toNull = true
                }
            }

            if (toNull) elfe1.nextPos = null
        }

        moved = elfes.filter { it.nextPos != null }.isNotEmpty()

        for (elfe in elfes) {
            map[elfe.position.y][elfe.position.x] = '.'

            if (elfe.nextPos == null) {
                map[elfe.position.y][elfe.position.x] = '#'
            } else {
                map[elfe.nextPos!!.y][elfe.nextPos!!.x] = '#'
                elfe.position = elfe.nextPos!!
                elfe.nextPos = null
            }
        }

        if (cpt == 10) {
            var firstPoint = elfes.minOf { it.position.x } to elfes.minOf { it.position.y }
            var secondPoint = elfes.maxOf { it.position.x } to elfes.maxOf { it.position.y }
            println("Part 1 : " + ((secondPoint.second - firstPoint.second + 1) * (secondPoint.first - firstPoint.first + 1) - elfes.size).toString())
        }
    }



    return cpt.toString()
}

fun noElfesAround(pos: Point): Boolean {
    for (y in pos.y - 1..pos.y + 1) {
        for (x in pos.x - 1 .. pos.x + 1) {
            if (y == pos.y && x == pos.x) continue
            if (map[y][x] == '#') return false
        }
    }
    return true
}


fun main() {
    var content = readInput("d23/input")

    var p1 = measureTimeMillis {
        println("Part 2 : " + part1(content))
    }

    println("Whole time : {$p1}")

}