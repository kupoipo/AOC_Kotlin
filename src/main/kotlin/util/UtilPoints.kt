package util

import java.math.BigDecimal
import kotlin.math.abs
import kotlin.math.atan2

enum class Direction(val dx: Int, val dy: Int, val sign: Char) {

    LEFT(-1, 0, '<'), RIGHT(1, 0, '>'), UP(0, -1, '^'), DOWN(0, 1, 'v'), NONE(0, 0, ' ');

    operator fun times(step: Int): Point = Point(this.dx * step, this.dy * step)
    fun opposite(): Direction {
        return when (this) {
            LEFT -> RIGHT
            RIGHT -> LEFT
            UP -> DOWN
            DOWN -> UP
            else -> NONE
        }
    }

    fun right(): Direction {
        return when (this) {
            LEFT -> UP
            UP -> RIGHT
            RIGHT -> DOWN
            else -> LEFT
        }
    }

    fun left(): Direction {
        return when (this) {
            LEFT -> DOWN
            DOWN -> RIGHT
            RIGHT -> UP
            else -> LEFT
        }
    }

    companion object {
        fun fromChar(char: Char): Direction {
            return when (char) {
                'U', 'N' -> Direction.UP
                'R', 'E' -> Direction.RIGHT
                'L', 'W' -> Direction.LEFT
                'D', 'S' -> Direction.DOWN
                else -> throw Exception("No direction found for $char")
            }
        }
    }
}

data class Point(var x: Long, var y: Long) : Comparable<Point> {
    fun up(): Point = this + Direction.UP
    fun down(): Point = this + Direction.DOWN
    fun right(): Point = this + Direction.RIGHT
    fun left(): Point = this + Direction.LEFT

    fun angleWith(other: Point) : Double{
        val dx = other.x - x
        val dy = other.y - y

        return atan2(dy.toDouble(), dx.toDouble())
    }

    fun sameDirection(v: Point) = x * v.x + y * v.y > 0

    fun collinear(v: Point) = x * v.y - y * v.x == 0L

    fun reduceToSmallestVector() : Point{
        val res = this.copy()


        if ((res.y != 0L || res.x != 0L)) {
            val pgcd = PGCD(res.x.toInt(), res.y.toInt())
            res.x /= abs(pgcd)
            res.y /= abs(pgcd)
        }

        if (res.y == 0L) {
            res.x = if (res.x > 0) 1 else -1
        }

        if (res.x == 0L) {
            res.y = if (res.y > 0) 1 else -1
        }

        return res
    }

    fun rotateRight() {
        val temp = x
        this.x = -y
        this.y = temp
    }

    fun rotateLeft() {
        val temp = x
        this.x = y
        this.y = -temp
    }

    var id = ID++

    constructor(p: Point) : this(p.x, p.y)

    constructor(x: Int, y: Int) : this(x.toLong(), y.toLong())

    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(other.x - x, other.y - y)
    operator fun times(n: Int) = Point(x * n, y * n)
    fun manhattan(other: Point = Point(0, 0)) = StrictMath.abs(other.x - x) + StrictMath.abs(other.y - y)

    fun moveInDirection(direction: Char, step: Int = 1): Point = when (direction) {
        'N', 'U' -> this + (Direction.UP * step)
        'S', 'D' -> this + (Direction.DOWN * step)
        'W', 'L' -> this + (Direction.LEFT * step)
        'E', 'R' -> this + (Direction.RIGHT * step)
        else -> throw IllegalArgumentException("$direction is not a valid direction")
    }


    fun forEachNeighbors(function: (Point) -> Unit) {
        Direction.values().dropLast(1).forEach {
            function(this + it)
        }
    }

    fun forEachEveryNeighbors(withPoint: Boolean = false, function: (Point) -> Unit) {
        function(this + Point(-1, -1))
        function(this + Direction.UP)
        function(this + Point(1, -1))
        function(this + Direction.LEFT)
        if (withPoint) function(this)
        function(this + Direction.RIGHT)
        function(this + Point(-1, 1))
        function(this + Direction.DOWN)
        function(this + Point(1, 1))
    }

    override fun toString(): String {
        return "[y=$y,x=$x]"
    }

    operator fun plus(d: Direction): Point {
        return Point(x + d.dx, y + d.dy)
    }

    fun <T> inMap(map: Matrix<T>): Boolean {
        if (this.x < 0 || this.y < 0) return false
        if (this.y >= map.size) return false
        if (this.x >= map[0].size) return false

        return true
    }

    override fun compareTo(other: Point): Int {
        if (other.y != y) return y.compareTo(other.y)
        return x.compareTo(other.x)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Point) {
            return other.x == this.x && other.y == this.y
        }
        return false
    }

    fun adjacent(diagonals: Boolean = true, includeItself: Boolean = false): List<Point> {
        return buildList {
            for (dy in -1..1) {
                for (dx in -1..1) {
                    if (dx != 0 || dy != 0) {
                        if (!diagonals && dx != 0 && dy != 0) {

                        } else {
                            add(Point(this@Point.x + dx, this@Point.y + dy))
                        }
                    }
                }
            }

            if (includeItself)
                add(this@Point)
        }
    }

    fun <T> outOfMap(map: Matrix<T>): Boolean {
        return this.x < 0 || this.y < 0 || this.x >= map[0].size || this.y >= map.size
    }

    override fun hashCode(): Int {
        return this.toString().hashCode()
    }


    companion object {
        var ID = 1
    }
}

enum class DIRECTION3D(val dx: Int, val dy: Int, val dz: Int) {
    X1(1, 0, 0), X_MINUS_1(-1, 0, 0), Y1(0, -1, 0), Y_MINUS_1(-0, 1, 0), Z1(0, 0, -1), Z_MINUS_1(-0, 0, 1)
}

data class Point3DLong(var x: Long, var y: Long, var z: Long) {
    operator fun plus(other: Point3DLong) = Point3DLong(other.x + x, other.y + y, other.z + z)
    operator fun minus(other: Point3DLong) = Point3DLong(other.x - x, other.y - y, other.z - z)
    operator fun times(n: Int) = Point3DLong(x * n, y * n, z * n)

    fun getNeighbors(): List<Point3DLong> {
        val neighbors = mutableListOf<Point3DLong>()
        DIRECTION3D.values().forEach {
            var newPoint = Point3DLong(x + it.dx, y + it.dy, z + it.dz)
            neighbors.add(newPoint)
        }


        return neighbors
    }

    fun all26Neighbors(): List<Point3DLong> {
        val neighbors = mutableListOf<Point3DLong>()

        for (z in -1..1)
            for (y in -1..1)
                for (x in -1..1)
                    if (z != 0 || y != 0 || x != 0)
                        neighbors.add(Point3DLong(this.x + x, this.y + y, this.z + z))


        return neighbors
    }

    fun manhattan(other: Point3DLong = Point3DLong(0, 0, 0)): Long {
        return kotlin.math.abs(x - other.x) + kotlin.math.abs(y - other.y) + kotlin.math.abs(z - other.z)
    }

    override fun toString(): String = "[$x,$y,$z]"
}

data class Point4DLong(val x: Long, val y: Long, val z: Long, val w: Long) {
    //operator fun plus(other: Point3DLong) = Point3DLong(other.x + x, other.y + y, other.z + z)
    //operator fun minus(other: Point3DLong) = Point3DLong(other.x - x, other.y - y, other.z - z)
    //operator fun times(n: Int) = Point3DLong(x * n, y * n, z * n)


    fun allNeighbors(): List<Point4DLong> {
        val neighbors = mutableListOf<Point4DLong>()

        for (w in -1..1)
            for (z in -1..1)
                for (y in -1..1)
                    for (x in -1..1)
                        if (z != 0 || y != 0 || x != 0 || w != 0)
                            neighbors.add(Point4DLong(this.x + x, this.y + y, this.z + z, this.w + w))


        return neighbors
    }
    /* fun manhattan(other: Point3DLong  = Point3DLong(0,0,0)) : Long {
         return kotlin.math.abs(x - other.x) + kotlin.math.abs(y - other.y) + kotlin.math.abs(z - other.z)
     }*/

    override fun toString(): String = "[$x,$y,$z,$w]"
}


data class Point3DBigDecimal(val x: BigDecimal, val y: BigDecimal, val z: BigDecimal) {
    operator fun plus(other: Point3DBigDecimal) = Point3DBigDecimal(other.x + x, other.y + y, other.z + z)
    operator fun minus(other: Point3DBigDecimal) = Point3DBigDecimal(other.x - x, other.y - y, other.z - z)
    operator fun times(n: BigDecimal) = Point3DBigDecimal(x * n, y * n, z * n)
    operator fun div(n: BigDecimal) = Point3DBigDecimal(x / n, y / n, z / n)

    fun getNeighbors(): List<Point3DBigDecimal> {
        val neighbors = mutableListOf<Point3DBigDecimal>()
        DIRECTION3D.values().forEach {
            var newPoint = Point3DBigDecimal(x + BigDecimal(it.dx), y + BigDecimal(it.dy), z + BigDecimal(it.dz))
            neighbors.add(newPoint)
        }
        return neighbors
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Point3DBigDecimal)
            return false
        return x == other.x && y == other.y && z == other.z
    }

    override fun toString(): String = "[$x,$y,$z]"
}

fun calculateGaussianSurface(points: List<Point>): Long {
    require(points.size >= 3 && points.first() == points.last()) {
        "Points list must be a closed polygon with at least three points"
    }
    var surface = 0.0
    val n = points.size

    for (i in 0 until n - 1) {
        surface += (points[i].x * points[i + 1].y - points[i + 1].x * points[i].y)
    }

    surface += (points[n - 1].x * points[0].y - points[0].x * points[n - 1].y)

    return (kotlin.math.abs(surface) / 2.0).toLong()
}
