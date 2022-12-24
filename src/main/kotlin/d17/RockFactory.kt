package d17

import util.Direction
import util.Matrix
import util.matrixOf

data class Rock(var piece : Matrix<Char>) {
    var x = STARTING_POINT.x
    var y = STARTING_POINT.y
    fun getHeight() = piece.size
    fun getWidth() = piece[0].size
    fun move(dir: Direction) {
        x += dir.dx
        y += dir.dy
    }

    fun unmove(dir: Direction) {
        x -= dir.dx
        y -= dir.dy
    }
}

class RockFactory() {
    companion object {
        var next = 0

        fun getCross() : Rock {
            var res = matrixOf(MutableList<MutableList<Char>>(3) { MutableList<Char>(3) { ROCK } })
            res[0][0] = EMPTY
            res[0][2] = EMPTY
            res[2][0] = EMPTY
            res[2][2] = EMPTY

            return Rock(res)
        }

        fun getLine(slope : Int) : Rock {
            return if (slope == 1)
                Rock(matrixOf(MutableList<Char>(4) {ROCK}))
            else
                Rock(matrixOf(MutableList<MutableList<Char>>(4) {MutableList<Char>(1) { ROCK } } ))
        }

        fun getL() : Rock {
            var res = matrixOf(MutableList<MutableList<Char>>(3) { MutableList<Char>(3) { ROCK } })
            res[0][0] = EMPTY
            res[0][1] = EMPTY
            res[1][0] = EMPTY
            res[1][1] = EMPTY

            return Rock(res)
        }

        fun getSquare() : Rock {
            return Rock(matrixOf(MutableList<MutableList<Char>>(2) { MutableList<Char>(2) { ROCK } } ) )
        }

        fun getNext() : Rock  = when((next++)%5) {
            (0) -> getLine(1)
            (1) -> getCross()
            (2) -> getL()
            (3) -> getLine(0)
            else -> getSquare()
        }
    }

}