package _2018.d22

import util.Day
import util.Point
import util.State
import java.awt.*
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane

class Visualisation(val day: Day22) : JFrame() {
    val gear = JLabel(Day22.Gear.TORCH.toString())
    val time = JLabel("0")
    val zone = Zone(day, Point(0, 0))

    init {
        layout = BorderLayout()

        val right = JPanel()
        right.layout = GridLayout(2, 1)
        right.add(time)
        right.add(gear)

        val center = JScrollPane(zone)

        this.add(right, BorderLayout.EAST)
        this.add(center)
        this.extendedState = MAXIMIZED_BOTH
        this.isVisible = true
        this.defaultCloseOperation = EXIT_ON_CLOSE

        Thread {
            val solution = State.shortestPastFrom(Day22.SaveState(null, 0, Point(0, 0), Day22.Gear.TORCH))!!.rebuildPath().reversed()

            for (state in solution) {
                if (state == null) continue
                Thread.sleep(200)
                val s = state as Day22.SaveState
                zone.position = s.p
                gear.text = s.g.toString()
                time.text = s.time.toString()
                repaint()
            }
        }.start()
    }
}

class Zone(val day: Day22, var position: Point) : JPanel() {
    private val casePixel = 3
    private val _lines = Day22.mapModulo.size
    private val _columns = Day22.mapModulo[0].size
    private val _height = casePixel * _lines
    private val _width = casePixel * _columns

    init {
        this.preferredSize = Dimension(_width, _height)
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        val g2 = g as Graphics2D
        g2.clearRect(0, 0, 10000, 10000)

        for (y in Day22.mapModulo.indices) {
            for (x in Day22.mapModulo[0].indices) {
                if (position == Point(x, y)) g2.color = Color.RED
                else if (position == Day22.target) Color.YELLOW
                else
                    when (Day22.mapModulo[y][x]) {
                        Day22.ROCKY -> g2.color = Color.GRAY
                        Day22.NARROW -> g2.color = Color.GREEN
                        else -> g2.color = Color.BLUE
                    }

                g2.fillRect(x * casePixel, y * casePixel, casePixel, casePixel)
            }
        }

        g2.color = Color.BLACK
        repeat(_lines + 1) {
            g2.drawLine(0, it * casePixel, _width, it * casePixel)
        }

        repeat(_columns + 1) {
            g2.drawLine(it * casePixel, 0, it * casePixel, _height)
        }
    }
}

fun main() {
    Visualisation(Day22("_2018/d22/input.txt"))
}