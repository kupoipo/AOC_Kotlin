package _2016.d11

import _2022.d20.li
import util.Day
import util.State
import util.allInts
import util.readFullText
import kotlin.math.abs
import kotlin.system.measureNanoTime

val microshipName = "HLPTCYBV"

data class StateElevator(
    override var parent: State?,
    override var time: Int,
    val elevator: Int,
    val chips: MutableList<Int>,
    val generator: MutableList<Int>,
    val minFloor: Int
) : State(parent, time) {
    override fun isDeadLock(): Boolean {
        if (elevator > 3 || elevator < minFloor) return true
        if (chips.all { elevator != it } && generator.all { elevator != it }) return true

        for (indexChip in chips.indices) {
            val chipFloor = chips[indexChip]

            if (generator[indexChip] != chipFloor) {
                if (generator.any { it == chipFloor })
                    return true

            /*for (indexG in generator.indices) {
                    if (generator[indexG] == chipFloor && chips[indexG] != chipFloor)
                        return true
                }*/
            }
        }

        return false
    }

    override fun timeWeighed(): Int {
        return time + chips.indices.sumOf { abs(chips[it] - generator[it]) } + chips.sumOf { 3 - it } + generator.sumOf { 3 - it }
    }

    override fun nextStates(): MutableList<State> {
        val chipAvailable = chips.indices.filter { chips[it] == elevator }
        val generatorAvailable = generator.indices.filter { generator[it] == elevator }

        val minFloor = buildList { addAll(chips); addAll(generator) }.min()

        val res = mutableListOf<State>()
        val moveTwoUp = mutableListOf<State>()
        val moveOneUp = mutableListOf<State>()
        val moveOneDown = mutableListOf<State>()
        val moveTwoDown = mutableListOf<State>()

        for (i1 in chipAvailable.indices) {
            val c = chipAvailable[i1]

            moveOneUp.add(
                StateElevator(
                    this,
                    time + 1,
                    elevator + 1,
                    chips.toMutableList().apply { this[c] += 1 },
                    generator.toMutableList(),
                    minFloor
                )
            )
            moveOneDown.add(
                StateElevator(
                    this,
                    time + 1,
                    elevator - 1,
                    chips.toMutableList().apply { this[c] -= 1 },
                    generator.toMutableList(),
                    minFloor
                )
            )

            for (i2 in i1 + 1 until chipAvailable.size) {
                val c2 = chipAvailable[i2]

                moveTwoUp.add(
                    StateElevator(
                        this,
                        time + 1,
                        elevator + 1,
                        chips.toMutableList().apply { this[c] += 1; this[c2] += 1 },
                        generator.toMutableList(),
                        minFloor
                    )
                )
                moveTwoDown.add(
                    StateElevator(
                        this,
                        time + 1,
                        elevator - 1,
                        chips.toMutableList().apply { this[c] -= 1; this[c2] -= 1 },
                        generator.toMutableList(),
                        minFloor
                    )
                )
            }
        }

        for (i1 in generatorAvailable.indices) {
            val c = generatorAvailable[i1]

            moveOneUp.add(
                StateElevator(
                    this,
                    time + 1,
                    elevator + 1,
                    chips,
                    generator.toMutableList().apply { this[c] += 1 }, minFloor
                ),
            )
            moveOneDown.add(
                StateElevator(
                    this,
                    time + 1,
                    elevator - 1,
                    chips,
                    generator.toMutableList().apply { this[c] -= 1 }, minFloor
                )
            )

            for (i2 in i1 + 1 until generatorAvailable.size) {
                val c2 = generatorAvailable[i2]

                moveTwoUp.add(
                    StateElevator(
                        this,
                        time + 1,
                        elevator + 1,
                        chips,
                        generator.toMutableList().apply { this[c] += 1; this[c2] += 1 }, minFloor
                    )
                )
                moveTwoDown.add(
                    StateElevator(
                        this,
                        time + 1,
                        elevator - 1,
                        chips,
                        generator.toMutableList().apply { this[c] -= 1; this[c2] -= 1 }, minFloor
                    )
                )
            }
        }

        for (c in chipAvailable) {
            for (g in generatorAvailable) {
                moveTwoUp.add(
                    StateElevator(
                        this,
                        time + 1,
                        elevator + 1,
                        chips.toMutableList().apply { this[c] += 1 },
                        generator.toMutableList().apply { this[g] += 1; }, minFloor
                    )
                )
                moveTwoDown.add(
                    StateElevator(
                        this,
                        time + 1,
                        elevator - 1,
                        chips.toMutableList().apply { this[c] -= 1 },
                        generator.toMutableList().apply { this[g] -= 1; }, minFloor
                    )
                )
            }
        }

        if (moveTwoUp.all { it.isDeadLock() }) {
            res.addAll(moveOneUp)
        } else {
            res.addAll(moveTwoUp)
        }

        if (moveOneDown.all { it.isDeadLock() }) {
            res.addAll(moveTwoDown)
        } else {
            res.addAll(moveOneDown)
        }

        return res
    }


    override fun isGoal(): Boolean {
        return chips.all { it == 3 } && generator.all { it == 3 }
    }

    override fun toString(): String {
        var res = "Time : $time\n"

        for (j in 3 downTo 0) {
            res += "F$j "

            res += if (elevator == j) "E  " else ".  "

            for (i in chips.indices) {
                val c = chips[i]
                val g = generator[i]

                res += if (g == j) {
                    microshipName[i] + "G "
                } else {
                    ".  "
                }

                res += if (c == j) {
                    microshipName[i] + "M "
                } else {
                    ".  "
                }
            }

            res += "\n"
        }

        return res
    }

    override fun equals(other: Any?): Boolean {
        val other = other as StateElevator

        val pairs = chips.zip(generator).sortedBy { it.first }
        val pairs2 = other.chips.zip(other.generator).sortedBy { it.first }

        return elevator == other.elevator && pairs == pairs2
    }

    override fun hashCode(): Int {

        val pairs = chips.zip(generator).sortedBy { it.first }
        var result = 17

        for ((first, second) in pairs) {
            result = 31 * result + first.hashCode()
            result = 31 * result + second.hashCode()
        }

        result = 31 * result + elevator.hashCode()

        return result
    }
}

class Day11(override val input: String) : Day<Long>(input) {
    var start: StateElevator

    init {
        input.split("\n").let { (floor, chips, generator) ->
            start = StateElevator(null, 1, floor.toInt(), chips.allInts(), generator.allInts(), 1)
        }
    }

    override fun solve1(): Long {
        start  = (State.shortestPastFrom(start)!!.rebuildPath(false).first() as StateElevator?)!!
        return start.time.toLong()
    }

    override fun solve2(): Long = start.time + 24L
}

fun main() {
    val day = Day11(readFullText("_2016/d11/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day11(readFullText("_2016/d11/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}