package _2018.d19

import _2018.register.Register2018
import _2023.d19.reg
import util.Day
import util.allInts
import util.readFullText
import kotlin.math.sqrt
import kotlin.system.measureNanoTime
class Day19(override val input : String) : Day<Long>(input) {
    private val register = Register2018()
    private var pointer : Int
    private val pointerBindTo : String
    private val instructions : List<String>

    init {
        input.split("\n").let {
            pointer = 0
            pointerBindTo = it.first().allInts().first().toString()
            instructions = it.drop(1)
        }
    }
    override fun solve1(): Long {
        while (pointer < instructions.size) {
            register[pointerBindTo] = pointer
            val data = instructions[pointer].split(" ")
            val function = register.getFunction(data.first())
            function(data[1], data[2], data.last())
            pointer = register[pointerBindTo]
            pointer++
        }
        return register["0"].toLong()
    }
    override fun solve2(): Long {
        /**
         * Change 977 with the value of your r3 after some instructions
         */
        val r5 = 977 + 10550400
        var res = 0

        for (i in 1..sqrt(r5.toDouble()).toInt()) {
            if (r5%i == 0)
                res += i + (r5/i)
        }
        return res.toLong()
    }
}

fun main() {
    val day = Day19(readFullText("_2018/d19/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day19(readFullText("_2018/d19/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}