package util

enum class Direction(val dx: Int, val dy : Int) {
    LEFT(-1, 0), RIGHT(1,0), UP(0,-1), DOWN (0,1), NONE(0,0);

    operator fun times(step: Int): Point = Point(this.dx * step, this.dy * step)
}

data class Point(var x : Int, var y : Int) {
    constructor(p : Point) : this(p.x, p.y)

    operator fun plus(other : Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(other.x - x, other.y - y)
    operator fun times(n: Int) = Point(x * n, y * n)
    fun manhattan(other : Point) = StrictMath.abs(other.x - x) + StrictMath.abs(other.y - y)

    fun moveInDirection(direction: Char, step: Int = 1): Point = when (direction) {
        'N', 'U' -> this + (Direction.UP * step)
        'S', 'D' -> this + (Direction.DOWN * step)
        'W', 'L' -> this + (Direction.LEFT * step)
        'E', 'R' -> this + (Direction.RIGHT * step)
        else -> throw IllegalArgumentException("$direction is not a valid direction")
    }

    fun forEachNeighbors(function : (Point) -> Unit) {
        Direction.values().dropLast(1).forEach {
            function(this + it)
        }
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
}


enum class DIRECTION3D(val dx : Int, val dy : Int, val dz : Int) {
    X1(1,0,0), X_MINUS_1(-1,0,0),Y1(0,-1,0), Y_MINUS_1(-0,1,0),Z1(0,0,-1), Z_MINUS_1(-0,0,1)
}
data class Point3D(val x : Int, val y : Int, val z : Int) {
    operator fun plus(other: Point3D) = Point3D(other.x + x, other.y + y, other.z + z)
    operator fun minus(other: Point3D) = Point3D(other.x - x, other.y - y, other.z -z)
    operator fun times(n: Int) = Point3D    (x * n, y * n, z * n)

    fun getNeighbors() : List<Point3D> {
        val neighbors = mutableListOf<Point3D>()
        DIRECTION3D.values().forEach {
            var newPoint = Point3D(x + it.dx, y + it.dy, z + it.dz)
            neighbors.add(newPoint)
        }
        return neighbors
    }
    override fun toString(): String = "[$x,$y,$z]"
}

