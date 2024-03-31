package _2020.d4

import util.Day
import util.findAllMatch
import util.readFullText
import kotlin.system.measureNanoTime

class Day4(override val input: String) : Day<Long>(input) {
    private val rawPassport = input.split("\n\n").map { line ->
        line.findAllMatch("[^ : \\n]*:[^ :\\n]*").associate { keyValue ->
            keyValue.split(":").let { it.first() to it.last() }
        }
    }

    private val requiredKeys = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    override fun solve1(): Long = rawPassport.count { it.keys.containsAll(requiredKeys) }.toLong()

    override fun solve2(): Long = rawPassport.filter { it.keys.containsAll(requiredKeys) }.filter { passport ->
        if (passport["byr"]!!.toInt().let { it < 1920 || it > 2002 }) return@filter false
        if (passport["iyr"]!!.toInt().let { it < 2010 || it > 2020 }) return@filter false
        if (passport["eyr"]!!.toInt().let { it < 2020 || it > 2030 }) return@filter false
        if (!passport["hgt"]!!.matches(Regex("""(1([5-8][0-9]|9[0-3])cm|(59|6[0-9]|7[0-6])in)"""))) return@filter false
        if (!passport["hcl"]!!.matches(Regex("""#[0-9a-f]{6}"""))) return@filter false
        if (!passport["ecl"]!!.matches(Regex("""(amb|blu|brn|gry|grn|hzl|oth)"""))) return@filter false
        if (!passport["pid"]!!.matches(Regex("""\d{9}"""))) return@filter false

        return@filter true
    }.size.toLong()
}

fun main() {
    val day = Day4(readFullText("_2020/d4/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day4(readFullText("_2020/d4/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}