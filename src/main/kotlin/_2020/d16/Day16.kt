package _2020.d16

import util.Day
import util.allInts
import util.allLong
import util.readFullText
import kotlin.math.abs
import kotlin.system.measureNanoTime

class Day16(override val input: String) : Day<Long>(input) {
    private val ranges = mutableListOf<List<LongRange>>()
    private val ticket: List<Long>
    private val otherTickers: List<List<Long>>

    init {
        val lines = input.split("\n").toMutableList()
        var line = lines.removeFirst()

        while (line != "") {
            line.allLong().let { ints ->
                ranges.add(
                    buildList {
                        for (i in 0..ints.lastIndex step 2) {
                            add(ints[i]..abs(ints[i + 1]))
                        }
                    })
            }

            line = lines.removeFirst()
        }

        lines.removeFirst()
        ticket = lines.removeFirst().allLong()

        lines.removeFirst()
        lines.removeFirst()
        otherTickers = lines.map { it.allLong() }
    }

    private fun invalidTicket(ticket: List<Long>) = ticket.firstOrNull { num ->
        ranges.all { !it.first().contains(num) && !it.last().contains(num) }
    }

    override fun solve1(): Long = otherTickers.mapNotNull { invalidTicket(it) }.sumOf { it }.toLong()

    override fun solve2(): Long {
        val mapFieldToRange = mutableMapOf<Int, Int>()
        val allTickets = otherTickers.toMutableList().apply { add(ticket) }.filter { invalidTicket(it) == null }
        val fieldToPossible = mutableListOf<Pair<Int, List<Boolean>>>()

        for (field in ticket.indices) {
            val possibleRanges = MutableList(ticket.size) { false }

            for (i in possibleRanges.indices) {
                possibleRanges[i] =
                    allTickets.all { t -> ranges[i].any { it.contains(t[field]) || it.contains(t[field]) } }
            }

            fieldToPossible.add(field to possibleRanges)
        }

        fieldToPossible.sortBy { it.second.count { it } }

        for (field in fieldToPossible) {
            val i = field.first
            val list = field.second.mapIndexed { index, it -> if (index !in mapFieldToRange.values) it else false }

            mapFieldToRange[i] = list.indexOfFirst { it }
        }

        val departureFields = mapFieldToRange.filter { it.value < 6 }.map { it.key.toLong() }

        var res = 1L
        departureFields.forEach { res *= ticket[it.toInt()] }

        return res
    }
}

fun main() {
    val day = Day16(readFullText("_2020/d16/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day16(readFullText("_2020/d16/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}