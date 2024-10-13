package _2019.d2

import _2019.IntCode
import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day2(override val input : String) : Day<Int>(input) {
    val intCode = IntCode(input)
    override fun solve1(): Int {
        while(!intCode.executeOneInstruction());

        return intCode.data[0].toInt()
    }
    override fun solve2(): Int {
        val tab_init = input.split(",").map { s -> s.toInt() }.toMutableList()


        for (noun in 1..99) {
            for (verb in 1..99) {
                var tab = tab_init.toMutableList()
                tab[1] = noun
                tab[2] = verb

                for (i in 0..tab.size step 4) {
                    when (tab[i]) {
                        1 -> {
                            tab[tab[i+3]] = tab[tab[i+1]] + tab[tab[i+2]]
                        }

                        2 -> {
                            tab[tab[i+3]] = tab[tab[i+1]] * tab[tab[i+2]]
                        }

                        99 -> {
                            break
                        }
                    }
                }

                if (tab[0] == 19690720) {
                    return noun * 100 + verb
                }
            }
        }



        return -1
    }
}

fun main() {
    //var day = Day2(readFullText("_2019/d2/test"))
    var day = Day2(readFullText("_2019/d2/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day2(readFullText("_2019/d2/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}