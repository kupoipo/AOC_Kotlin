package _2020.d22

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

class Day22(override val input: String) : Day<Long>(input) {
    enum class State {
        PLAYER_LOSE, PLAYER_WON, PLAYER_WON_GAME
    }


    private val decks = input.split("\n\n").map { it.allInts().drop(1).toMutableList() }

    private fun round(playersCards: MutableList<Int>, crabsCards: MutableList<Int>, playersCard: Int, crabsCard: Int) {
        if (playersCard > crabsCard) {
            playersCards.add(playersCard)
            playersCards.add(crabsCard)
        } else {
            crabsCards.add(crabsCard)
            crabsCards.add(playersCard)
        }
    }

    private fun playRecursive(playersCards: MutableList<Int>, crabsCards: MutableList<Int>, subGame: Boolean): State {
        val previousRound = mutableSetOf<String>()

        while (playersCards.isNotEmpty() && crabsCards.isNotEmpty()) {
            val snapShot = "$playersCards $crabsCards"

            val playersCard = playersCards.removeFirst()
            val crabsCard = crabsCards.removeFirst()

            if (snapShot in previousRound) {
                return State.PLAYER_WON
            }
            previousRound.add(snapShot)

            if (playersCards.size >= playersCard && crabsCards.size >= crabsCard) {
                when (playRecursive(
                    playersCards.take(playersCard).toMutableList(),
                    crabsCards.take(crabsCard).toMutableList(),
                    true
                )) {
                    State.PLAYER_WON_GAME -> {
                        return State.PLAYER_WON_GAME
                    }

                    State.PLAYER_WON -> {
                        playersCards.add(playersCard)
                        playersCards.add(crabsCard)
                    }

                    else -> {
                        crabsCards.add(crabsCard)
                        crabsCards.add(playersCard)
                    }
                }
            } else {
                round(playersCards, crabsCards, playersCard, crabsCard)
            }
        }

        return if (playersCards.size > 0) State.PLAYER_WON else State.PLAYER_LOSE
    }

    override fun solve1(): Long {
        val decks = decks.map { it.toMutableList() }
        while (decks.all { it.size > 0 }) {
            round(decks.first(), decks.last(), decks.first().removeFirst(), decks.last().removeFirst())
        }

        return decks.first { it.size > 0 }.reversed().mapIndexed { index, i -> (index + 1) * i }.sum().toLong()
    }

    override fun solve2(): Long {
        println(decks)
        playRecursive(decks.first(), decks.last(), false)
        println(decks)
        return decks.first { it.size > 0 }.reversed().mapIndexed { index, i -> (index + 1) * i }.sum().toLong()
    }
}

fun main() {
    val day = Day22(readFullText("_2020/d22/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day22(readFullText("_2020/d22/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}