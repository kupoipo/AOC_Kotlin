package util

class Cube(val x: LongRange, val y: LongRange, val z: LongRange) {
    fun overlaps(other: Cube): Boolean {
        return x.isOverlapping(other.x)  && y.isOverlapping(other.y) && z.isOverlapping(other.z)
    }

    fun overlappedCube(other: Cube): Cube? {
        if (!overlaps(other)) return null

        return Cube(x.overlap(other.x), y.overlap(other.y), z.overlap(other.z))
    }

    fun minus(overlap: Cube): List<Cube> {
        val result = ArrayList<Cube>()

        for (remainingX in x.minus(overlap.x)) {
            result.add(Cube(remainingX, y, z))
        }
        for (remainingY in y.minus(overlap.y)) {
            result.add(Cube(overlap.x, remainingY, z))
        }
        for (remainingZ in z.minus(overlap.z)) {
            result.add(Cube(overlap.x, overlap.y, remainingZ))
        }

        return result
    }

    fun split(other: Cube): List<Cube> {
        val overlap = overlappedCube(other) ?: return listOf(this, other)

        val currentRemaining = this.minus(overlap)
        val otherRemaining = other.minus(overlap)

        return listOf(overlap) + currentRemaining + otherRemaining
    }

    override fun toString(): String {
        return "Cube(x=$x, y=$y, z=$z)"
    }
}