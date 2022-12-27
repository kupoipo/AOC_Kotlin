package _2021.d10

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis

enum class TYPE(val open : Char, val close : Char, val score : Long, val scoreClosing : Long) {
    PARENTHESE('(', ')', 3, 1),
    CROCHET('[', ']', 57, 2),
    BRACKET('{', '}', 1197, 3),
    CHEVRON('<', '>', 25137, 4)
}
class Day10(override val input : String) : Day<Long>(input) {
    val lines = input.split("\n")

    fun typeFromOpening(c : Char) : TYPE? = when (c) {
        '(' -> TYPE.PARENTHESE
        '[' -> TYPE.CROCHET
        '{' -> TYPE.BRACKET
        '<' -> TYPE.CHEVRON
        else -> null
    }

    fun typeFromClosing(c : Char) : TYPE? = when (c) {
        ')' -> TYPE.PARENTHESE
        ']' -> TYPE.CROCHET
        '}' -> TYPE.BRACKET
        '>' -> TYPE.CHEVRON
        else -> null
    }
    fun scoreFromLine(line : String) : Long {
        val pile = mutableListOf<TYPE>()

        line.forEach {
            var type = typeFromOpening(it)

            if (type == null) {
                type = typeFromClosing(it)

                if (pile.removeLast().close != it)
                    return type!!.score
            } else {
                pile.add(type)
            }
        }

        return 0
    }

    fun pileToComplete(line : String) : MutableList<TYPE>? {
        val pile = mutableListOf<TYPE>()

        line.forEach {
            var type = typeFromOpening(it)

            if (type == null) {
                if (pile.removeLast().close != it)
                    return null
            } else {
                pile.add(type)
            }
        }

        return pile
    }

    override fun solve1(): Long {
        return lines.map { scoreFromLine(it) }.sum()
    }
    override fun solve2(): Long {
        val values = mutableListOf<Long>()

        lines.forEach{
            val pile = pileToComplete(it)
            if (pile != null) {
                var score = 0L

                pile.reversed().forEach {
                    score *= 5
                    score += it.scoreClosing
                }

                values.add(score)
            }
        }

        return values[values.size/2]
    }
}

fun main() {
    //var day = Day10(readFullText("_2021/d10/test"))
    var day = Day10(readFullText("_2021/d10/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 1 : " + day.solve2()) }
    println("Temps partie 1 : {$t2}")
}