package _2023.d4

import util.Day
import util.allInts
import util.listOfMatch
import util.readFullText
import kotlin.math.pow
import kotlin.system.measureTimeMillis

class Card(val card : List<Int>, val winningValue : List<Int> ) {
    fun nbNumberWinning() : Int = card.count { winningValue.contains(it) }
}
class Day4(override val input : String) : Day<Long>(input) {
    val cards = mutableListOf<Card>()

    init {
        input.split("\n").forEachIndexed{ index, line ->
            val groups = Regex("""(\d+ *)+(\||$)""").listOfMatch(line)
            val tas = groups.map { it.allInts() }
            cards.add(Card(tas.first(), tas.last()))
        }
    }
    override fun solve1(): Long = cards.sumOf {
        val nbWinning = it.nbNumberWinning()
        if (nbWinning != 0)
            2.0.pow((it.nbNumberWinning() - 1).toDouble())
        else
            0.0
    }.toLong()

    fun sumOfCard(i : Int) : Int {
        var total = 0
        val card = cards[i]
        val nbWinning = card.nbNumberWinning()
        total += nbWinning

        for (j in i+1..i+total) {
            if (j <= cards.size)
                total += sumOfCard(j)
        }

        return total
    }

    override fun solve2(): Long {
        return cards.indices.sumOf { 1 + sumOfCard(it).toLong() }
    }
}

fun main() {
    //var day = Day4(readFullText("_2023/d4/test"))
    var day = Day4(readFullText("_2023/d4/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day4(readFullText("_2023/d4/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}