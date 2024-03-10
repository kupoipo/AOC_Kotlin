package _2017.d23

import _2017.register.Register
import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day23(override val input: String) : Day<Long>(input) {
    override fun solve1(): Long {
        val register = Register(input.split("\n"))
        while (register.hasNext()) {
            register.next()
        }
        return register.nbInstructions["mul"]!!
    }

    override fun solve2(): Long {
        val register = Register(input.split("\n"))

        register["a"] = 1
        register["b"] = 6500 + 100000
        register["c"] = register["b"] + 17000
        do {
            register["f"] = 1
            register["d"] = 2

            var i = 2
            while (i * i < register["b"]) {
                if (register["b"] % i == 0L) {
                    register["f"] = 0
                }
                i++
            }

            if (register["f"] == 0L) {
                register["h"] = register["h"] + 1
            }

            register["g"] = register["b"] - register["c"]
            register["b"] = register["b"] + 17
        } while (register["g"] != 0L)

        return register["h"]
    }
}

fun main() {
    val day = Day23(readFullText("_2017/d23/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day23(readFullText("_2017/d23/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}