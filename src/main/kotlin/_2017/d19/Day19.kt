package _2017.d19

import _2022.d20.li
import util.*
import kotlin.system.measureNanoTime

class Day19(override val input: String) : Day<String>(input) {
    var index = 0L
    val map = matrixFromString(input, '.') { it }
    val start = Point(map.first().indices.first { map[0][it] == '|' }, 0)
    override fun solve1(): String {

        val listLetter = mutableListOf<Char>()
        var direction = Direction.DOWN
        var position = start

        while (direction != Direction.NONE) {
            while (map[position] !in "+Y") {
                position += direction

                index++
                if (map[position] in 'A'..'Z')
                    listLetter += map[position]

            }

            direction = if (direction == Direction.DOWN || direction == Direction.UP) {
                if ((position + Direction.RIGHT).let { it.inMap(map) && map[it] == '-' })
                    Direction.RIGHT
                else if ((position + Direction.LEFT).let { it.inMap(map) && map[it] == '-' })
                    Direction.LEFT
                else
                    Direction.NONE
            } else {
                if ((position + Direction.DOWN).let { it.inMap(map) && map[it] == '|' })
                    Direction.DOWN
                else if ((position + Direction.UP).let { it.inMap(map) && map[it] == '|' })
                    Direction.UP
                else
                    Direction.NONE
            }

            position += direction
            index++
            println(listLetter)
        }

        return listLetter.joinToString("")
    }

    override fun solve2(): String {
        return index.toString()
    }
}

fun main() {
    val day = Day19(readFullText("_2017/d19/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day19(readFullText("_2017/d19/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}