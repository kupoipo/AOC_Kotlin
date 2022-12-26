package _2021.d4

import util.*
import kotlin.system.measureTimeMillis

data class Grid(val values : Matrix<Int>) {
    private fun lineOver(line : Int) : Boolean = (values[line].indices).count{ values[line][it] > 0 } == 0
    private fun colOver(col : Int)   : Boolean = (values.indices).count{ values[it][col] > 0 } == 0

    fun isOver() : Boolean {
        values.indices.forEach    { if (lineOver(it)) return true }
        values[0].indices.forEach { if (colOver(it)) return true }

        return false
    }

    fun putNb(draw: Int) {
        values.forEachIndexed {
            indexLine, line -> line.forEachIndexed {
                indexCol, nb -> if (nb == draw) values[indexLine][indexCol] = -nb
            }
        }
    }

    fun score(): Int = values.sumOf { it.sumOf { if (it > 0) it else 0 } }
}

class Day4(override val input : String) : Day<Int>(input) {
    private val draw : MutableList<Int>
    private val grids : MutableList<Grid>

    init {
        val values = input.replace("\r", "").split("\n\n").let{ it[0] to it.drop(1) }

        draw = values.first.split(",").map { it.toInt() }.toMutableList()

        grids = buildList {
            values.second.forEach {
                val temp = buildList{
                        it.split("\n").toMutableList().map{ it.split(" ").filter { it != "" }.map{ it.toInt() }.let { add(it.toMutableList()) }
                    }
                }.toMutableList()
                add(Grid(matrixOf(temp)))
            }
        }.toMutableList()
    }

    override fun solve1(): Int {
        while (draw.isNotEmpty()) {
            val nb = draw.removeFirst()

            grids.forEach { it.putNb(nb) }
            grids.filter { it.isOver() }.forEach {
                grids.remove(it);
                return it.score() * nb
            }
        }

        return -1
    }
    override fun solve2(): Int {
        var nb = 0
        var last : Grid? = null
        while (grids.isNotEmpty()) {
            nb = draw.removeFirst().toInt()

            grids.forEach { it.putNb(nb) }
            
            grids.filter { it.isOver() }.forEach { grids.remove(it); last = it }
        }

        return last!!.score() * nb
    }
}

fun main() {
    //var day = Day4(readFullText("_2021/d4/test"))
    var day = Day4(readFullText("_2021/d4/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 1 : " + day.solve2()) }
    println("Temps partie 1 : {$t2}")
}