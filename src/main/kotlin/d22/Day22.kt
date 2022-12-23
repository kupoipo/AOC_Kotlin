package d22
import Day
import Matrix
import Point
import afficheMap
import java.io.File
import kotlin.system.measureTimeMillis

const val EMPTY = '.'
const val VOID  = ' '
const val WALL  = '#'

enum class Direction(val facing : Int, val vector : Point) {
    RIGHT(0, Point(1, 0)), DOWN(1, Point(0,1)), LEFT(2, Point(-1, 0)), UP(3, Point(0,-1))
}

class Day22(text : String) : Day() {
    val map : Matrix<Char>
    private var position : Point
    private var direction : Direction
    private val instruction : String
    val width : Int; var height : Int
    init {
        val (field, instruction) = text.split("\n\n")
        this.instruction = instruction
        var content = field.split("\n")

        width = content[0].length
        map = mutableListOf()
        position = Point(y = 0, x = content[0].indexOf(EMPTY))
        direction  = Direction.RIGHT

        content.forEach {
            var temp = it.toMutableList()
            while (temp.size < width) temp.add(VOID)
            map.add(temp)
        }

        height = map.size
    }

    fun nextCase(inst : String) : Point {
        var posTemp: Point = Point(position)
        var dx: Int;
        var dy: Int
        var turn = (inst.contains("R") || inst.contains("L"))

        run repeatBlock@ {
            repeat(if (turn) inst.dropLast(1).toInt() else inst.toInt()) {
                dx = posTemp.x; dy = posTemp.y
                do {
                    dx = (dx + direction.vector.x) % width
                    dy = (dy + direction.vector.y) % height

                    if (dx < 0) dx = width - 1
                    if (dy < 0) dy = height - 1
                } while (map[dy][dx] == VOID);

                if (map[dy][dx] == WALL) return@repeatBlock
                posTemp.x = dx; posTemp.y = dy
            }
        }

        if (turn) {
            direction = if (inst.takeLast(1) == "R") {
                Direction.values()[(direction.facing+1)%4]
            } else {
                Direction.values()[if ((direction.facing-1) < 0) 3 else direction.facing - 1]
            }
        }

        return posTemp
    }


    override fun solve1(content: String): String {
        afficheMap(map)
        for (inst in instruction.split(Regex("((?<=R)|(?<=L))"))) {
            var temp = nextCase(inst)
            position.x = temp.x
            position.y = temp.y
        }


        return (1000 * (1 + position.y) + 4 * (1 + position.x) + direction.facing).toString()
    }

    override fun solve2(content: String): String {
        return ""
    }

}

fun main() {
    var content = File("src/main/kotlin/d22/input.txt").readText()

    var d = Day22(content)

    var p1 = measureTimeMillis {
        println("Part 1 : " + d.solve1(""))
    }

    println("Part 1 : {$p1}")

    p1 = measureTimeMillis {
        println("Part 2 : " + d.solve2(""))
    }

    println("Part 2 : {$p1}")
}