package d23
import Point
import afficheMap
import matrixOf
import readInput

import kotlin.system.measureTimeMillis

const val DECALAGE = 20
const val SIZE = 40

data class Elfe(var position : Point, var nextPos : Point? = null) {
    var order = mutableListOf(::canMoveNorth, ::canMoveSouth, ::canMoveWest, ::canMoveEast)

    override fun toString(): String {
        return "En $position - va en : $nextPos"
    }

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
}

val posElfes = mutableSetOf<Elfe>()
val map = matrixOf(MutableList<MutableList<Char>>(SIZE) { MutableList<Char>(SIZE) { '.'} } )
var nbElfes = 0

fun part1(content : List<String>) : String {
    for (line in content.withIndex()) {
        for (case in line.value.withIndex()) {
            if (case.value == '#') {
                posElfes.add(Elfe(Point(y = DECALAGE + line.index, x = DECALAGE + case.index)))
                map[DECALAGE + line.index][DECALAGE + case.index] = '#'
                nbElfes++
            }
        }
    }

    repeat(10) {
        afficheMap(map)
        for (elfe in posElfes) {
            var p = elfe.position
            if (noElfesAround(p)) continue

            for (i in elfe.order.indices)
                if (elfe.order[i]()) break

            var temp = elfe.order.removeAt(0)
            elfe.order.add(temp)


        }

        for (elfe1 in posElfes) {
            var toNull = false
            if (elfe1.nextPos == null) continue
            for (elfe2 in posElfes) {
                if (elfe1 != elfe2 && elfe1.nextPos == elfe2.nextPos) {
                    elfe2.nextPos = null
                    toNull = true
                }
            }

            if (toNull) elfe1.nextPos = null
        }

        for (elfe in posElfes) {
            map[elfe.position.y][elfe.position.x] = '.'

            if (elfe.nextPos == null) {
                map[elfe.position.y][elfe.position.x] = '#'
            } else {
                map[elfe.nextPos!!.y][elfe.nextPos!!.x] = '#'
                elfe.position = elfe.nextPos!!
                elfe.nextPos = null
            }
        }
    }

    afficheMap(map)

    return ""
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

fun part2(content : List<String>) : String {

    return ""
}

fun main() {
    var content = readInput("d23/test")

    var p1 = measureTimeMillis {
        println("Part 1 : " + part1(content))
    }

    println("Part 1 : {$p1}")

    p1 = measureTimeMillis {
    }

    println("Part 2 : {$p1}")
}