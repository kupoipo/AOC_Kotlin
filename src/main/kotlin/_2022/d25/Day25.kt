package _2022.d25
import util.Day
import util.readFullText
import kotlin.math.pow

val base5 = mapOf<Char, Long>('1' to 1, '2' to 2, '=' to -2, '0' to 0, '-' to -1)

class Day25(override val input : String) : Day<String>(input) {
    var numbers = input.split("\n")

    private fun toDecimal(line : String) : Long{
        val line = line.reversed()

        return line.mapIndexed { index, it -> 5.0.pow(index.toDouble()) * base5[it]!! }.sum().toLong()
    }

    private fun toSnafu(value : Long) : String {
        var nb = value
        val res = mutableListOf<Char>()
        while (nb>0) {
            val digit = nb % 5

            nb /= 5

            res.add(0, when(digit){
                0L->'0'
                1L->'1'
                2L->'2'
                3L-> {nb++; '=' }
                else -> {
                    nb++; '-'
                }
            })
        }
        return res.joinToString(separator = "")
    }

    override fun solve1(): String {
        return toSnafu(numbers.map { toDecimal(it) }.sum())
    }

    override fun solve2(): String {
        return ""
    }

}

fun main() {
    val d = Day25(readFullText("d25/input"))


    println(d.solve1())
    println(d.solve2())
}