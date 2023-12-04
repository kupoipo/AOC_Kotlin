package _2023.d4

import util.Day
import util.allInts
import util.listOfMatch
import util.readFullText
import kotlin.math.pow
import kotlin.system.measureTimeMillis

class Card(card : List<Int>, winningValue : List<Int> ) {
    var nbWinningCard: Int = card.count { winningValue.contains(it) }
}
class Day4(override val input : String) : Day<Long>(input) {
    private val cards = mutableListOf<Card>()

    init {
        input.split("\n").forEachIndexed{ index, line ->
            val groups = Regex("""(\d+ *)+(\||$)""").listOfMatch(line)
            val tas = groups.map { it.allInts() }
            cards.add(Card(tas.first(), tas.last()))
        }
    }
    override fun solve1(): Long = cards.sumOf {
        if (it.nbWinningCard != 0)
            2.0.pow((it.nbWinningCard - 1).toDouble())
        else
            0.0
    }.toLong()

    private fun sumOfCard(i : Int) : Int {
        val total = cards[i].nbWinningCard

        return total + (i+1..i+total).sumOf { j ->
            if (j <= cards.size)
                sumOfCard(j)
            else
                0
        }
    }

    override fun solve2(): Long = cards.indices.sumOf { 1 + sumOfCard(it).toLong() }
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