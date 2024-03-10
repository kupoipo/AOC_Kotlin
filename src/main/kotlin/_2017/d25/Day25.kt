package _2017.d25

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime


class Day25(override val input : String) : Day<Long>(input) {
    class TuringState(private val id: String, private val ifZero: List<String>, private val ifOne: List<String>) {
        fun accept() : TuringState {
            val state = if (turingBluePrint.getOrPut(position) { 0 } == 0) ifZero else ifOne

            turingBluePrint[position] = state.first().toInt()
            position += state[1].toInt()
            return turingStates[state.last()]!!
        }

        override fun toString(): String {
            val ifs = listOf(ifZero, ifOne)
            var strRes = "In state $id:\n"

            for (i in ifs.indices) {
                val currentIf = ifs[i]
                strRes += "\tIf the current value is $i:\n"
                strRes += "\t\t- Write the value ${currentIf[0]}.\n"
                strRes += "\t\t- Move one slot to the ${if (currentIf[1] == "-1") "left" else "right"}.\n"
                strRes += "\t\t- Continue with state ${currentIf[2]}.\n"

            }

            strRes += "\n"
            return strRes
        }

        companion object {
            var position = 0
            var turingBluePrint = mutableMapOf<Int, Int>()
            var turingStates = mutableMapOf<String, TuringState>()

            fun start() {
                position = 0
                turingBluePrint = mutableMapOf()
                turingStates = mutableMapOf()
            }
        }
    }

    private var nbRep : Int
    private var currentState : TuringState
    init {
        TuringState.start()
        var lines = input.split("\n")
        nbRep = lines[1].allInts().first()
        lines = lines.drop(3)

        for (i in 0..lines.size step 10) {
            val stringState = lines.slice(i until i+9)
            val idChar = stringState.first().takeLast(2).dropLast(1)
            val ifZero = listOf(
                stringState[2].takeLast(2).dropLast(1),
                if (stringState[3].contains("right")) "1" else "-1",
                stringState[4].takeLast(2).dropLast(1)
            )
            val ifOne = listOf(
                stringState[6].takeLast(2).dropLast(1),
                if (stringState[7].contains("right")) "1" else "-1",
                stringState[8].takeLast(2).dropLast(1)
            )

            val state = TuringState(idChar, ifZero, ifOne)
            TuringState.turingStates[idChar] = state

            println(state)
        }

        currentState = TuringState.turingStates["A"]!!
    }

    override fun solve1(): Long {
        repeat(nbRep) {
            currentState = currentState.accept()
        }

        return TuringState.turingBluePrint.values.count { it == 1 }.toLong()
    }
    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day25(readFullText("_2017/d25/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day25(readFullText("_2017/d25/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}