package _2016.d19

import util.Day
import util.readFullText
import java.util.*
import kotlin.system.measureNanoTime

class Day19(override val input: String) : Day<Long>(input) {
    var elfes = MutableList(input.toInt()) { it }

    override fun solve1(): Long {
        while (elfes.size != 1) {
            elfes = if (elfes.size % 2 == 1) {
                elfes.filterIndexed { index, _ -> (index + 1) % 2 == 1 }.drop(1).toMutableList()
            } else {
                elfes.filterIndexed { index, _ -> (index + 1) % 2 == 1 }.toMutableList()
            }
        }
        return elfes.first().toLong() + 1
    }

    override fun solve2(): Long {
        var elfes = MutableList(input.toInt()) { it }

        while (elfes.size > 2) {
            val indexSitRemoved = mutableSetOf<Int>()
            var numSit = elfes.size / 2
            var incrementation = if (elfes.size % 2 == 1) 2 else 1

            /**
             * Number of removed sit following the sequence : 2, 2, 3, 4, 4, 5, 6, 6, ...
             * So the number of removed sit at the N position for N%3 = 0,1 is : ( ( N/3 ) - 1 * 2
             * And ( ( N/3 ) - 1 * 2 + 1 for N%3 = 2
             */
            val nbSitRemoved: Int = when ((elfes.size - 3) % 3) {
                0, 1 -> 2 + ( ((elfes.size - 3) / 3) * 2)
                else -> 2 + ((elfes.size - 3) / 3 * 2) + 1
            }

            /**
             * Next, the sit removed follow the next sequence :
             *    - 3 sit : [1, 0]
             *    - 4 sit : [2, 3]
             *    - 5 sit : [2, 4, 0]
             *    - 6 sit : [3, 4, 0, 1]
             *    - 7 sit : [3, 5, 6, 1]
             *    - 8 sit : [4, 5, 7, 0, 2]
             *    - 9 sit : [4, 6, 7, 0, 1, 3]
             *    - ...
             *
             * We note that the deleted seats always start at N/2, and the following ones are determined by +1, +2, +1,...
             * So we can generate the list of index to delete, and delete them, going forward until there is only two sit left and take the first one
             */
            repeat(nbSitRemoved) {
                indexSitRemoved.add(numSit)
                numSit = (numSit + incrementation) % elfes.size
                incrementation = if (incrementation == 2) 1 else 2
            }

            elfes = elfes.filterIndexed { index, _ -> index !in indexSitRemoved }.toMutableList()
        }

        return elfes.first().toLong() + 1
    }
}



fun main() {
    val day = Day19(readFullText("_2016/d19/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")



    println()
    println()

    val dayTest = Day19(readFullText("_2016/d19/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}