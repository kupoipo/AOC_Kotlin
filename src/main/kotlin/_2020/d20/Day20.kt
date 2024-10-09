package _2020.d20

import util.*
import kotlin.math.sqrt
import kotlin.system.measureNanoTime

class Day20(override val input: String) : Day<Long>(input) {
    class Picture(val picture: Matrix<Char>, val id: Long) {
        /**
         * Each side is a 4-array binary representation of the rotate side [0°, 90°, 180°, 270°]
         */
        val sides = mutableListOf<MutableList<List<Int>>>()
        var orientation = 0
        var flip = 0

        init {
            repeat(2) {
                sides.add(createRotations(picture))
                picture.flipY()
            }
        }

        private fun createRotations(picture: MutableList<MutableList<Char>>): MutableList<List<Int>> {
            val temp = mutableListOf<List<Int>>()

            val top = intsFromLine(picture.first(), true)
            val right = intsFromLine(picture.map { it.last() }, false)
            val bot = intsFromLine(picture.last(), true)
            val left = intsFromLine(picture.map { it.first() }, false)

            temp.add(listOf(top[0], right[0], bot[0], left[0]))
            temp.add(listOf(left[1], top[1], right[1], bot[1]))
            temp.add(listOf(bot[2], left[2], top[2], right[2]))
            temp.add(listOf(right[3], bot[3], left[3], top[3]))

            return temp
        }

        private fun intsFromLine(line: List<Char>, isTopOrBotSide: Boolean): List<Int> {
            val iBinary = line.joinToString("").replace("#", "1").replace(".", "0")
            val i = iBinary.toInt(2)
            val iReverse = iBinary.reversed().toInt(2)

            return if (isTopOrBotSide) {
                listOf(i, i, iReverse, iReverse)
            } else {
                listOf(i, iReverse, iReverse, i)
            }
        }

        fun isSide(p: Picture): Boolean {
            return sides.flatten()
                .any { sideThis -> p.sides.flatten().any { sideOther -> sideThis.any { sideOther.contains(it) } } }
        }

        override fun toString(): String {
            return "$id - Flip : $flip - Orientation : $orientation"
        }
    }

    private val pictures = input.split("\n\n").map { line ->
        Picture(matrixFromString(line.dropWhile { it != '\n' }.drop(1), '.') { it }, line.firstInt().toLong())
    }

    private val mapPicture =
        pictures.associateWith { picture -> pictures.filter { it != picture && it.isSide(picture) } }

    private var fullPicture: Matrix<Picture?> =
        emptyMatrixOf(sqrt(pictures.size.toDouble()).toInt(), sqrt(pictures.size.toDouble()).toInt(), null)

    private var fullPictureFlatten: Matrix<Char> = emptyMatrixOf(
        fullPicture.size * pictures[0].picture.size - 2 * fullPicture.size,
        fullPicture[0].size * pictures[0].picture[0].size - 2 * fullPicture[0].size,
        ' '
    )

    private val corners = mapPicture.filter { it.value.size == 2 }

    private fun placePicture(point: Point, p: Picture, sens: Int): Boolean {
        var nextSens = sens
        fullPicture[point] = p

        val nextPoint: Point
        if (point.x + sens >= fullPicture[0].size || point.x + sens < 0) {
            nextPoint = Point(point.x, point.y + 1)
            nextSens *= -1
        } else {
            nextPoint = Point(point.x + sens, point.y)
        }

        for (flip in 0..1) {
            p.flip = if (flip == 1) 0 else 1
            for (i in 0..3) {
                p.orientation = i

                /**
                 * If the position/orientation doesn't match all neighbour
                 */
                if (point.left()
                        .inMap(fullPicture) && fullPicture[point.left()] != null && fullPicture[point.left()].let { it!!.sides[it.flip][it.orientation][1] != p.sides[p.flip][p.orientation][3] }
                ) {
                    continue
                }

                if (point.right()
                        .inMap(fullPicture) && fullPicture[point.right()] != null && fullPicture[point.right()].let { it!!.sides[it.flip][it.orientation][3] != p.sides[p.flip][p.orientation][1] }
                ) {
                    continue
                }

                if (point.up()
                        .inMap(fullPicture) && fullPicture[point.up()] != null && fullPicture[point.up()].let { it!!.sides[it.flip][it.orientation][2] != p.sides[p.flip][p.orientation][0] }
                ) {
                    continue
                }

                if (point.down()
                        .inMap(fullPicture) && fullPicture[point.down()] != null && fullPicture[point.down()].let { it!!.sides[it.flip][it.orientation][0] != p.sides[p.flip][p.orientation][2] }
                ) {
                    continue
                }

                /**
                 * We reached the bottom without conflict
                 */
                if (nextPoint.y >= fullPicture.size) return true

                for (nextPicture in mapPicture[p]!!) {
                    if (fullPicture.any { it.contains(nextPicture) }) continue
                    if (placePicture(nextPoint, nextPicture, nextSens)) return true;
                    fullPicture[nextPoint] = null
                }
            }
        }

        p.orientation = 0
        p.flip = 0
        fullPicture[point] = null
        return false
    }

    private val seeMonster = arrayOf("..................#.", "#....##....##....###", ".#..#..#..#..#..#...")

    private fun searchSeeMonster(img: Matrix<Char>) : Int {
        val setMonster = mutableSetOf<Point>()

        for (l in 0 until img.size - seeMonster.size) {
            for (c in 0 until img[0].size - seeMonster[0].length) {
                if (isMonster(l,c,img)) {
                    /**
                     * Maybe two see monster are overlapping, so we use a set
                     */
                    for (y in l until l +seeMonster.size) {
                        for (x in c until c + seeMonster[0].length) {
                            if (seeMonster[y - l][x - c] == '#') {
                                setMonster.add(Point(x, y))
                            }
                        }
                    }
                }
            }
        }

        return setMonster.size
    }

    private fun isMonster(l: Int, c: Int, img: MutableList<MutableList<Char>>): Boolean {
        for (y in l until l +seeMonster.size) {
            for (x in c until c + seeMonster[0].length) {
                if (seeMonster[y - l][x - c] == '#' && img[y][x] != '#') return false
            }
        }

        return true
    }

    private fun createFullPictureFlatten() {
        fullPicture.forEachIndexed { lig, line ->
            line.forEachIndexed { col, picture ->
                picture!!.picture.withoutSides().forEachIndexed { l_picture, chars ->
                    chars.forEachIndexed { c_picture, c ->
                        val column = col * (picture.picture.size-2) + c_picture
                        val row = lig * chars.size + l_picture

                        this.fullPictureFlatten[row][column] = c
                    }
                }
            }
        }
    }

    init {
        for (c in corners) {
            if (placePicture(Point(0, 0), c.key, 1)) {
                break
            }
        }

        for (p in pictures) {
            if (p.flip == 1) {
                p.picture.flipY()
            }

            repeat(p.orientation) {
                p.picture.rotateRight()
            }
        }


    }

    override fun solve1(): Long =
        corners.map { it.key.id }.reduce { acc, i -> acc * i }

    override fun solve2(): Long {
        val possibilities = mutableListOf<Int>()
        repeat(2) {
            fullPicture.flipY()
            repeat(4) {
                fullPicture.rotateRight()

                /**
                 * We create the sub-image for the 8 possible rotations
                 */

                createFullPictureFlatten()

                repeat(2) {
                    fullPictureFlatten.flipY()
                    repeat(4) {
                        fullPictureFlatten.rotateRight()

                        /**
                         * For the 8 possible rotations of the sub-image, we try to find sea monsters in it
                         */
                        searchSeeMonster(fullPictureFlatten).let {
                            if (it != 0) {
                                val flattenImage = fullPictureFlatten.map { it.joinToString("") }.joinToString("\n")
                                possibilities.add(flattenImage.count { it == '#' } - it)

                            }
                        }
                    }
                }
            }
        }
        return possibilities.min().toLong()
    }
}

fun main() {

    val day = Day20(readFullText("_2020/d20/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")
    val dayTest = Day20(readFullText("_2020/d20/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}