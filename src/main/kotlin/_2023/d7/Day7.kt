package _2023.d7

import util.Day
import util.readFullText
import java.util.PriorityQueue
import kotlin.system.measureTimeMillis

const val CARDS = "23456789TJQKA"
const val CARDS_JOKER = "J23456789TQKA"

class Hand(val cards: String, val bid: Int, val strength: Int, var joker: Boolean) : Comparable<Hand> {
    companion object {
        fun handFromLine(input: String, joker: Boolean = false): Hand {
            input.split(" ").let { (cards, bid) ->
                var map = CARDS.associateWith { c ->  cards.count { it == c } }

                if (joker) {
                    val nbJoker = map['J']

                    if (nbJoker != 0) {
                        map = map.filter { it.key != 'J' }.toMutableMap()

                        val max = map.maxOf { it.value }

                        for (k in map.keys) {
                            if (map[k] == max) {
                                map[k] = map[k]!! + nbJoker!!
                                break
                            }
                        }
                    }
                }

                when (map.maxOf { it.value }) {
                    5 -> return Hand(cards, bid.toInt(), 7, joker)
                    4 -> return Hand(cards, bid.toInt(), 6, joker)
                    3 -> {
                        if (map.containsValue(2))
                            return Hand(cards, bid.toInt(), 5, joker)
                        return Hand(cards, bid.toInt(), 4, joker)
                    }

                    2 -> {
                        if (map.count { it.value == 2 } == 2)
                            return Hand(cards, bid.toInt(), 3, joker)
                        return Hand(cards, bid.toInt(), 2, joker)
                    }

                    else -> return Hand(cards, bid.toInt(), 1, joker)
                }
            }
        }
    }

    override fun compareTo(other: Hand): Int {
        val powerCards = if (joker) CARDS_JOKER else CARDS

        if (strength != other.strength) return strength.compareTo(other.strength)
        else {
            for (i in cards.indices) {
                if (cards[i] != other.cards[i])
                    return powerCards.indexOf(cards[i]).compareTo(powerCards.indexOf(other.cards[i]))

            }
        }

        return 0
    }

    override fun toString(): String {
        return "$cards - $bid - $strength"
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