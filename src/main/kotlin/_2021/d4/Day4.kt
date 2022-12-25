package _2021.d4

import util.*
import kotlin.system.measureTimeMillis

data class Grid(val values : Matrix<String>) {
    private val found = emptyMatrixOf(values.size, values[0].size, false)

    private fun lineOver(line : Int) : Boolean = (found[line].indices).count{ !found[line][it] } == 0
    private fun colOver(col : Int)   : Boolean = (found.indices).count{ !found[it][col] } == 0

    fun isOver() : Boolean {
        for (lig in found.indices) {
            if (lineOver(lig)) return true
        }

        for (col in found[0].indices) {
            if (colOver(col)) return true
        }

        return false
    }

    fun putNb(draw: String) {
        values.forEachIndexed {
            indexLine, line -> line.forEachIndexed {
                indexCol, nb -> if (nb == draw) found[indexLine][indexCol] = true
            }
        }
    }

    fun score(): Int {
        var score = 0
        values.forEachIndexed { lig, line ->
            line.forEachIndexed { col, case ->
                if (!found[lig][col])
                    score += case.toInt()
            }
        }
        return score
    }
}

class Day4(override val input : String) : Day<Int>(input) {
    val draw : MutableList<String>
    val grids : MutableList<Grid>

    init {
        val values = input.split("\n\n").let{ it[0] to it.drop(1) }

        draw = values.first.split(",").toMutableList()
        grids = buildList {
            values.second.forEach {
                val temp = buildList{
                    it.split("\n").toMutableList().map{ it.split(" ").filter { it != "" }.let { add(it.toMutableList()) }
                    } }.toMutableList()
                add(Grid(matrixOf(temp)))
            }
        }.toMutableList()
    }

    override fun solve1(): Int {
        while (draw.isNotEmpty()) {
            val nb = draw.removeFirst()

            grids.forEach {
                it.putNb(nb)
                if (it.isOver()) {
                    return it.score() * nb.toInt()
                }
            }
        }

        return -1
    }
    override fun solve2(): Int {
        var nb = 0
        var last : Grid? = null
        while (grids.isNotEmpty()) {
            nb = draw.removeFirst().toInt()

            grids.forEach { it.putNb(nb.toString()) }
            
            grids.filter { it.isOver() }.forEach { grids.remove(it); last = it }
        }

        return last!!.score() * nb
    }
}

fun main() {
    //var day = Day4(readFullText("_2021/d4/test"))
    var day = Day4(readFullText("_2021/d4/input"))

    val t1 = measureTimeMillis { println("Part 1 : " )}//+ day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 1 : " + day.solve2()) }
    println("Temps partie 1 : {$t2}")
}