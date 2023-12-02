package _2023.d2

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis

const val MAX_RED  = 12
const val MAX_GREEN  = 13
const val MAX_BLUE  = 14

class Tas(var red : Int = 0, var green : Int = 0, var blue : Int = 0) {
    fun isValid() = red <= MAX_RED && blue <= MAX_BLUE && green <= MAX_GREEN
}
class Day2(override val input : String) : Day<Long>(input) {
    override fun solve1(): Long = input.split("\n").map{ it.substring(it.indexOf(":") + 2) }.mapIndexed{ index, s ->
            var isValid = true

            s.split("; ").forEach { t ->
                val tas = Tas()

                t.split(",").forEach { el ->
                    val el = el.strip().split(" ")

                    val nb = el[0].toInt()

                    when (el[1]) {
                        "green" -> tas.green = nb
                        "red" -> tas.red = nb
                        "blue" -> tas.blue = nb
                    }
                }

                isValid = isValid && tas.isValid()
            }

           if (isValid) index+1 else 0
        }.sum().toLong()


    override fun solve2(): Long {
        var total = 0L

        input.split("\n").map{ it.substring(it.indexOf(":") + 2) }.forEachIndexed { index, s ->
            val allTas = s.split("; ")
            val tasMin = Tas()

            for (t in allTas) {
                t.split(",").forEach { el ->
                    val el = el.strip().split(" ")

                    val nb = el[0].toInt()

                    //val c = (tasMin::class.members)

                    val field = tasMin.javaClass.getDeclaredField("green")
                    if (nb > field.getInt(tasMin))
                        field.setInt(tasMin, nb)


                }
            }

            total += tasMin.green * tasMin.red * tasMin.blue
        }

        return total
    }
}

fun main() {
    //var day = Day2(readFullText("_2023/d2/test"))
    var day = Day2(readFullText("_2023/d2/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day2(readFullText("_2023/d2/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}