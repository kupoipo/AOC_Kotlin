package d25
import java.lang.Math.abs

fun part1(content : List<String>) : String {
    
    return ""
}

fun part2(content : List<String>) : String {

    return ""
}

fun main() {
        val x = 1111
        val y = 2456

        val trees = abs(y - x)

        println(x)
        println(y)
        println( List<Int>(12){x + if (it != 0) (abs(y-x)) * (it) else 0}.sum() )

}