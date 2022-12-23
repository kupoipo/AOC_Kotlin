package d24
import readInput
import kotlin.system.measureTimeMillis

fun part1(content : List<String>) : String {
    
    return ""
}

fun part2(content : List<String>) : String {

    return ""
}

fun main() {
    var content = readInput("d21/input")

    var p1 = measureTimeMillis {
        println("Part 1 : " + d21.part1())
    }

    println("Part 1 : {$p1}")

    p1 = measureTimeMillis {
        println("Part 2 : " + d21.part2())
    }

    println("Part 2 : {$p1}")
}