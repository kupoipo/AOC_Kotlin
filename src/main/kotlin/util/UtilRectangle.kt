package util

import java.awt.geom.Rectangle2D
import kotlin.math.max
import kotlin.math.min

/**
 *
 * @constructor Build a rectangle with the top left corner being the lowest pair (x,y) and the bottom right corner the
 *              highest pair (x,y)
 *
 */
class Rectangle(x1: Long, y1: Long, x2: Long, y2: Long) : Comparable<Rectangle> {
    var xFrom: Long = 0L
    var yFrom: Long = 0L
    var xTo: Long = 0L
    var yTo: Long = 0L
    var height: Long = 0L
    var width: Long = 0L

    init {
        xFrom = min(x1, x2)
        xTo = max(x1, x2)
        yFrom = min(y1, y2)
        yTo = max(y1, y2)
        width = xTo - xFrom + 1
        height = yTo - yFrom + 1
    }

    val perimeter = height * 2 + width * 2
    val area = (height) * width
    private val topLeft = Point(xFrom, yFrom)
    private val topRight = Point(xTo, yFrom)
    private val bottomRight = Point(xTo, yTo)
    private val bottomLeft = Point(xFrom, yTo)
    private val topSegment = Segment(topLeft, topRight)
    private val rightSegment = Segment(topRight, bottomRight)
    private val bottomSegment = Segment(bottomRight, bottomLeft)
    private val leftSegment = Segment(topLeft, bottomLeft)

    val segments = listOf(topSegment, rightSegment, bottomSegment, leftSegment)
    val points = listOf(topLeft, topRight, bottomRight, bottomLeft)
    override fun compareTo(other: Rectangle): Int {
        return -area.compareTo(other.area)
    }

    override fun toString(): String {
        return "Rectangle($xFrom, $yFrom, $xTo, $yTo)"
    }


}


class Segment(val from: Point, val to: Point) {


    fun intersect(other: Segment): Boolean {
        val v1x = to.x - from.x
        val v1y = to.y - from.y
        val v2x = other.to.x - other.from.x
        val v2y = other.to.y - other.from.y

        if ((v1x == 0L && v1y == 0L) || (v2x == 0L && v2y == 0L)) return false

        val dot = v1x * v2x + v1y * v2y
        if (dot != 0L) return false

        fun cross(a: Point, b: Point, c: Point): Long {
            return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)
        }

        val p1 = from
        val q1 = to
        val p2 = other.from
        val q2 = other.to

        val c1 = cross(p1, q1, p2)
        val c2 = cross(p1, q1, q2)
        val c3 = cross(p2, q2, p1)
        val c4 = cross(p2, q2, q1)

        val s1 = java.lang.Long.signum(c1)
        val s2 = java.lang.Long.signum(c2)
        val s3 = java.lang.Long.signum(c3)
        val s4 = java.lang.Long.signum(c4)

        // ==> intersection stricte: chaque extrémité doit être
        // d'un côté strictement différent de l'autre segment
        return (s1 != 0 && s2 != 0 && s1 != s2) &&
                (s3 != 0 && s4 != 0 && s3 != s4)
    }
}
