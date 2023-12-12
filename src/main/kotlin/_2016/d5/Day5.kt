package _2016.d5

import util.Day
import util.md5
import util.readFullText
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.system.measureNanoTime
class Day5(override val input : String) : Day<String>(input) {
    override fun solve1(): String {
        var password = ""
        var i = 0L

        while (password.length != 8) {
            md5(input + i.toString()).let { hash ->
                if (hash.startsWith("00000")) {
                    password += hash[5]
                }
            }

            i++
        }

        return password
    }
    override fun solve2(): String {
        val password = MutableList(8) { ' '}
        var i = 0L

        while (password.any{ it == ' '}) {
            md5(input + i.toString()).let { hash ->
                if (hash.startsWith("00000")) {
                    if (hash[5].isDigit()) {
                        val i = hash[5].digitToInt()

                        if (i < password.size && password[i] == ' ') {
                            password[i] = hash[6]
                            println(password[i])
                        }
                    }
                }
            }

            i++
        }

        return password.joinToString("")
    }
}

fun main() {
    val day = Day5(readFullText("_2016/d5/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day5(readFullText("_2016/d5/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}