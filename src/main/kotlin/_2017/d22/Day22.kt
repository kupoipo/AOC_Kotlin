package _2017.d22

import util.Day
import util.Direction
import util.Point
import util.readFullText
import kotlin.collections.set
import kotlin.system.measureNanoTime

class Day22(override val input: String) : Day<Long>(input) {
    enum class VirusState {
        WEAK {
            override fun next(): VirusState {
                return INFECTED
            }
        }, INFECTED {
            override fun next(): VirusState {
                return FLAGGED
            }
        }, FLAGGED {
            override fun next(): VirusState? {
                return null
            }
        };

        abstract fun next() : VirusState?
    }

    private val infected = mutableMapOf<Point, VirusState>()
    private var virus = Point(0, 0)
    private var total = 0L
    private var facing = Direction.UP

    init {
        val lines = input.split("\n")
        virus = Point(lines.size / 2, lines.size / 2)
        for (y in lines.indices) {
            for (x in lines[y].indices) {
                if (lines[y][x] == INFECTED)
                    infected[Point(x - virus.x, y - virus.y)] = VirusState.INFECTED
            }
        }
        virus = Point(0,0)
    }

    fun step() {
        if (infected.contains(virus)) {
            facing = facing.right()
            infected.remove(virus)
        } else {
            facing = facing.left()
            infected[Point(virus)] = VirusState.INFECTED
            total++
        }

        virus += facing
    }

    private fun step2() {
        if (infected.contains(virus)) {
            facing = when (infected[virus]) {
                VirusState.INFECTED ->
                    facing.right()
                VirusState.FLAGGED -> facing.opposite()
                else -> facing
            }

            val nextState = infected[virus]!!.next()

            if (nextState == VirusState.INFECTED)
                total++

            if (nextState == null) {
                infected.remove(virus)
            } else {
                infected[virus] = nextState
            }
        } else {
            facing = facing.left()
            infected[Point(virus)] = VirusState.WEAK
        }

        virus += facing
    }

    override fun solve1(): Long {
        repeat(10_000) {
            step()
        }
        return total
    }

    override fun solve2(): Long {
        repeat(10_000_000) {
            step2()
        }

        return total
    }

    companion object {
        const val INFECTED = '#'
    }
}

fun main() {
    val day = Day22(readFullText("_2017/d22/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day22(readFullText("_2017/d22/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}