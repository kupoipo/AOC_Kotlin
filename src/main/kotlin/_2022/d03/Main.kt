package d3

import util.readInput

import java.io.File

fun part1(content : List<String>) : Int {
    var sum = 0
    for (line in content) {
        var ch = line.substring(0,line.length / 2)
        var ch2 = line.substring(line.length/2)

        val set = ch.toSet()

        for (el in set)
            if (el in ch2) {
                sum += if (el in 'a'..'z') {
                    (el - 'a' + 1)
                } else {
                    (el - 'A' + 27)
                }
                break
            }
    }

    return sum
}

fun prio(elt: Char): Int {
    if (elt in 'a'..'z')
        return 1 + (elt - 'a')
    return 27 + (elt -'A')
}

fun part2(content : List<String> ) : Int {
    var sum = 0

    for (index in content.indices step 3) {
        val s1 = content[index].toSet()
        val s2 = content[index+1].toSet()
        val s3 = content[index+2].toSet()

        sum += prio(s1.intersect(s2).intersect(s3).first())
    }

    return sum
}

fun main() {
    try {
        println(part2(readInput("d3/input")))
    } catch (e : Exception) {
        println(e)
    }

}