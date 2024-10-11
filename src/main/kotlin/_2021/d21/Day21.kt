package _2021.d21

import _2021.d18.split
import _2023.d24.Position
import util.Day
import util.allInts
import util.readFullText
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureNanoTime

class Day21(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    data class GameState(val p1State: PlayerState, val p2State: PlayerState, val p1Turn: Boolean) {

        fun hasWinner(score: Int) = p1State.score >= score || p2State.score >= score

        fun next(sumLaunch: Int) = GameState(
            if (p1Turn) p1State.next(sumLaunch) else p1State,
            if (p1Turn) p2State else p2State.next(sumLaunch),
            !p1Turn
        )

        override fun toString(): String {
            return "1 : $p1State - 2 : $p2State - $p1Turn"
        }
    }

    data class PlayerState(val position: Int, val score: Int) {
        fun next(sumLaunch: Int) = ((position + sumLaunch) % 10).let { newPosition ->
            PlayerState(
                newPosition,
                score + if (newPosition == 0) 10 else newPosition
            )
        }

        override fun toString(): String {
            return "Player in position $position - score $score"
        }
    }

    data class WinCount(val p1: Long, val p2: Long) {
        operator fun plus(other: WinCount): WinCount {
            return WinCount(p1 + other.p1, p2 + other.p2)
        }

        operator fun times(other: Long): WinCount {
            return WinCount(p1 * other, p2 * other)
        }
    }

    val initState =
        GameState(
            PlayerState(input.split("\n").first().allInts().last(), 0),
            PlayerState(input.split("\n").last().allInts().last(), 0),
            true
        )

    private fun playPart1(state: GameState, dice: Int, turn: Int): Long = when (state.hasWinner(1000)) {
        true -> (min(state.p1State.score, state.p2State.score) * turn).toLong()
        false -> {
            var launch = 0
            var dice = dice
            repeat(3) {
                dice++
                if (dice > 100) {
                    dice = 1
                }

                launch += dice
            }
            playPart1(state.next(launch), dice, turn + 3)
        }
    }

    private val cache = mutableMapOf<GameState, WinCount>()

    private val outcomes = (1..3).flatMap { x -> (1..3).flatMap { y -> (1..3).map { z -> x + y + z } } }
        .groupingBy { it }
        .eachCount()


    private fun playPart2(state: GameState): WinCount = when (state.hasWinner(21)) {
        true -> {
            if (state.p1State.score >= 21) WinCount(1, 0)
            else WinCount(0, 1)
        }

        false -> {
            cache.getOrPut(state) {
                var res = WinCount(0, 0)
                for ((key, value) in outcomes) {
                    res += playPart2(state.next(key)) * value.toLong()
                }
                res
            }
        }
    }

    override fun solve1(): Long = playPart1(initState, 0, 0)
    override fun solve2(): Long = playPart2(initState).let { max(it.p1, it.p2) }
}

fun main() {

    val day = Day21(false, readFullText("_2021/d21/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day21(true, readFullText("_2021/d21/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}