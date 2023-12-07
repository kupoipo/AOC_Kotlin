package _2023.d7

import util.Day
import util.readFullText
import java.util.PriorityQueue
import kotlin.math.pow
import kotlin.system.measureTimeMillis

const val CARDS = "23456789TJQKA"
const val CARDS_JOKER = "J23456789TQKA"

class Hand(val cards: String, val bid: Int, val strength: Double, var joker: Boolean) : Comparable<Hand> {
    companion object {
        fun handFromLine(input: String, joker: Boolean = false): Hand {
            input.split(" ").let { (cards, bid) ->
                val map = CARDS.associateWith { c -> cards.count { it == c }.toDouble() }.toMutableMap()

                if (joker) {
                    val nbJoker = map['J']

                    if (nbJoker != 0.0) {
                        map -= 'J'
                        val maxKey = map.maxByOrNull { it.value }?.key
                        maxKey?.let { map[it] = map[it]!! + nbJoker!! }
                    }
                }
                return Hand(cards, bid.toInt(), map.values.sumOf { it.pow(2.0) }, joker)

            }
        }
    }

    override fun compareTo(other: Hand): Int {
        val powerCards = if (joker) CARDS_JOKER else CARDS

        if (strength != other.strength) return strength.compareTo(other.strength)

        cards.indices.first { i -> cards[i] != other.cards[i] }.also {
            return powerCards.indexOf(cards[it]).compareTo(powerCards.indexOf(other.cards[it]))
        }
    }
}

class Day7(override val input: String) : Day<Long>(input) {
    val hands = mutableListOf<Hand>()
    val handsJoker = mutableListOf<Hand>()

    init {
        input.split("\n").forEach {
            hands.add(Hand.handFromLine(it))
            handsJoker.add(Hand.handFromLine(it, true))
        }

        hands.sort()
        handsJoker.sort()
    }

    override fun solve1(): Long = hands.mapIndexed { index, hand -> (index + 1) * hand.bid }.sum().toLong()
    override fun solve2(): Long = handsJoker.mapIndexed { index, hand -> (index + 1) * hand.bid }.sum().toLong()
}

fun main() {
    val day = Day7(readFullText("_2023/d7/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    val dayTest = Day7(readFullText("_2023/d7/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}
