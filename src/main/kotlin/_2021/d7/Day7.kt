package _2021.d7

import util.Day
import util.readFullText
import java.lang.Math.abs
import kotlin.system.measureTimeMillis
class Day7(override val input : String) : Day<Long>(input) {
    val numbers = input.split(",").map { it.toInt() }

    fun fuelTo(nb : Int) : Long = buildList { numbers.forEach { add(abs(it - nb).toLong()) } }.sum()
    fun fuelToV2(nb : Int) : Long = buildList { numbers.forEach {
        add(
            List(kotlin.math.abs(it - nb)) {it+1} .sum().toLong()
        )
    } }.sum()

    override fun solve1(): Long = buildList {  (0..numbers.max()).forEach {
           add(fuelTo(it))
       } }.min()

    override fun solve2(): Long =
        buildList {  (0..numbers.max()).forEach {
            add(fuelToV2(it))
        } }.min()

}

fun main() {
    //var day = Day7(readFullText("_2021/d7/test"))
    var day = Day7(readFullText("_2021/d7/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 1 : " + day.solve2()) }
    println("Temps partie 1 : {$t2}")
}