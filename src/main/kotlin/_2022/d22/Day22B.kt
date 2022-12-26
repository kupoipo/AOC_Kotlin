package _2022.d22

import util.Matrix
import util.Point
import util.showMap
import util.rotateRight
import java.io.File

const val SIZE = 50

var instruction : String = ""
val fullMap : Matrix<Char> = loadMap(File("src/main/kotlin/_2022.d22/input.txt").readText())
var position = Point(0,0)
var positionFace : Face? = null
var direction : Direction = Direction.RIGHT

fun loadMap(text: String): MutableList<MutableList<Char>> {
    val map : Matrix<Char> = mutableListOf()

    text.split("\n").forEach {
        if (it[0].isDigit()) {
            println(it)
            instruction = it
        }
        else map.add(it.toMutableList())
    }
    
    return map
}

val faceOne = Face(origin = Point(x = 50, y = 50), isTop = true)
val faceTwo = Face(Point(x = 100, y = 0), rotate = 0)
val faceThree = Face(Point(x = 50, y = 0), rotate = 0)
val faceFour = Face(Point(x = 50, y = 100))
val faceFive = Face(Point(x = 0, y = 100))
val faceSix  = Face(Point(x = 0, y = 150), isBot = true, rotate = 0)

class Face(var origin : Point, val isTop : Boolean = false, val isBot : Boolean = false, val rotate : Int = 0) {
    var left   : Face? = null
    var top    : Face? = null
    var right  : Face? = null
    var bottom : Face? = null
    var id     : Int
    var map    : Matrix<Char> = mutableListOf()

    init {
        for (y in origin.y until origin.y + 50) {
            map.add(mutableListOf())
            for (x in origin.x until origin.x + 50) {
                map.last().add(fullMap[y][x])
            }
        }

        id = ID++

        repeat(rotate) {
            map.rotateRight()
        }
    }

    override fun toString(): String {
        return "Face $id"
    }

    fun show(position: Point): Point {
        var x = position.x + origin.x+1
        var y = position.y + origin.y+1

        return Point(y = y, x = x)
    }

    companion object {
        var ID : Int = 1
    }
}

fun initFace() {
    faceOne.top = faceThree; faceOne.bottom = faceFour; faceOne.right = faceTwo; faceOne.left = faceFive
    faceTwo.top = faceSix; faceTwo.bottom = faceOne; faceTwo.right = faceFour; faceTwo.left = faceThree
    faceThree.top = faceSix; faceThree.bottom = faceOne; faceThree.right = faceTwo; faceThree.left = faceFive
    faceFour.top = faceOne; faceFour.bottom = faceSix; faceFour.right = faceTwo; faceFour.left = faceFive
    faceFive.top = faceOne; faceFive.bottom = faceSix; faceFive.right = faceFour; faceFive.left = faceThree
    faceSix.top = faceFive; faceSix.bottom = faceTwo; faceSix.right = faceFour; faceSix.left = faceThree

    positionFace = faceThree
}

fun solve2() : String {

    for (inst in instruction.split(Regex("((?<=R)|(?<=L))"))) {
        println(positionFace!!.show(position).toString() + "on $positionFace : " + inst.dropLast(1) + " to $direction")
       var (temp, face)  = nextCase(inst)
        position.x = temp.x
        position.y = temp.y
        positionFace = face
    }

   // println("Final position : $positionFace on " + positionFace!!.show(position) + " facing $direction")
    var final = positionFace!!.show(position)
    return (1000 * (final.y) + 4 * (final.x) + direction.facing).toString()

}

fun nextCase(inst: String): Pair<Point, Face> {
    var posTemp: Point = Point(position)
    var faceTemp : Face = positionFace!!
    var dx: Int; var dy: Int; var dface : Face; var ddirection : Direction

    var turn = (inst.contains("R") || inst.contains("L"))

    run end@ {
        repeat(if (turn) inst.dropLast(1).toInt() else inst.toInt()) {
            dx = posTemp.x; dy = posTemp.y; dface = faceTemp; ddirection = direction

            dx = posTemp.x + direction.vector.x
            dy = posTemp.y + direction.vector.y

            if (dy > 49) {
                dface = faceTemp.bottom!!
                when (faceTemp) {
                    (faceTwo) -> {dy = dx; dx = 49; ddirection = Direction.LEFT}
                    (faceFour) -> {dy = dx; dx = 49; ddirection = Direction.LEFT}
                    else -> {
                        dy = 0
                    }
                }
            }

            if (dy < 0) {
                dface = faceTemp.top!!

                when (faceTemp) {
                    (faceThree) -> { dy = dx ; dx = 0; ddirection = Direction.RIGHT }
                    (faceFive) -> {dy = dx; dx = 0; ddirection = Direction.RIGHT}
                    else -> {
                        dy = 49;
                    }
                }
            }

            if (dx < 0) {
                dface = faceTemp.left!!

                when (faceTemp) {
                    (faceOne) -> { dx = dy; dy = 0; ddirection = Direction.DOWN}
                    (faceThree) -> { dx = 0; dy = 49 - dy; ddirection = Direction.RIGHT }
                    (faceFive) -> {dx = 0; dy = 49 -dy; ddirection = Direction.RIGHT}
                    (faceSix) -> {dx = dy; dy = 0; ddirection = Direction.DOWN}
                    else -> {
                        dx = 49
                    }
                }
            }

            if (dx > 49) {
                dface = faceTemp.right!!

                when (faceTemp) {
                    (faceOne) -> {dx = dy; dy = 49; ddirection = Direction.UP}
                    (faceTwo) -> {dy = 49 - dy; dx = 49; ddirection = Direction.LEFT}
                    (faceFour) -> { dy = 49 - dy; dx = 49; ddirection = Direction.LEFT}
                    (faceSix) -> {dx = dy; dy = 49; ddirection = Direction.UP}
                    else -> {
                        dx = 0
                    }
                }
            }


            if (dface.map[dy][dx] != WALL) {
                direction = ddirection
                faceTemp = dface
                posTemp.x = dx; posTemp.y = dy;
            } else return@end
        }
    }


    if (turn) {
        direction = if (inst.takeLast(1) == "R") {
            Direction.values()[(direction.facing+1)%4]
        } else {
            Direction.values()[if ((direction.facing-1) < 0) 3 else direction.facing - 1]
        }
    }

    return posTemp to faceTemp
}

fun main() {
    initFace()

    showMap(faceSix.map)

    println(solve2())
}