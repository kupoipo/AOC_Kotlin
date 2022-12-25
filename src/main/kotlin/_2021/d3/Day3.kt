package _2021.d3

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day3(override val input : String) : Day<Int>(input) {
    private var lines = input.split("\n").toMutableList()

    private fun most(col : Int, lines : MutableList<String>) : Int {
        return if ((lines.indices).count { lines[it][col] == '0' } > lines.size / 2) 0 else 1
    }

    private fun less(col : Int, lines : MutableList<String>) : Int {
        return if ((lines.indices).count { lines[it][col] == '0' } > lines.size / 2) 1 else 0
    }

    override fun solve1(): Int {
        val binaryMost = buildString { lines[0].indices.forEach { append(most(it, lines)) } }
        val binaryLess = buildString { lines[0].indices.forEach { append(less(it, lines)) } }

        return binaryMost.toInt(2) * binaryLess.toInt(2)
    }

    fun getLast(typeSearch : (Int, MutableList<String>) -> Int) : String {
        var index = 0
        val temp = lines.toMutableList()

        while (temp.size > 1) {
            var bit = typeSearch(index, temp)

            temp.removeIf { it[index].digitToInt() == bit }
            index = (index + 1) % temp[0].length
        }

        return temp[0]
    }

    override fun solve2(): Int {
        val oxygeneBinary = getLast(::most)
        val coBinary      = getLast(::less)

        return oxygeneBinary.toInt(2) * coBinary.toInt(2)
    }
}

fun main() {
    //var day = Day3(readFullText("_2021/d3/test"))
    var day = Day3(readFullText("_2021/d3/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 1 : " + day.solve2()) }
    println("Temps partie 1 : {$t2}")
}