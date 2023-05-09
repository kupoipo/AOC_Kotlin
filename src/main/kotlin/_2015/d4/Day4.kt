package _2015.d4

import util.Day
import util.readFullText
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.system.measureTimeMillis
class Day4(override val input : String) : Day<String>(input) {
    override fun solve1(): String {
        var md5 = ""
        var key = input
        var index = 0

        while (!md5.startsWith("000000")) {
            key = input + index.toString()
            md5 = md5(key)
            index++
        }

        return key
    }
    override fun solve2(): String {
        return ""
    }

    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}

fun main() {
    //var day = Day4(readFullText("_2015/d4/test"))
    var day = Day4(readFullText("_2015/d4/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day4(readFullText("_2015/d4/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}