package _2025.d9

import util.Rectangle
import util.readFullText
import java.awt.*
import javax.swing.JFrame
import javax.swing.JPanel

class Canvas(val day: Day9): JPanel() {

    private fun drawRect(r: Rectangle, graphics2D: Graphics2D) {
        graphics2D.color = Color.RED

        graphics2D.drawRect((r.xFrom/100.0).toInt(), (r.yFrom/100.0).toInt(), (r.width/100.0).toInt(), (r.height/100.0).toInt())

        graphics2D.color = Color.BLACK
    }
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        val g2 = g as Graphics2D

        var current = day.points.first()
        for (p in day.points.drop(1)) {
            g2.drawLine((current.x/100.0).toInt(), (current.y/100.0).toInt(), (p.x/100.0).toInt(), (p.y/100.0).toInt())
            current = p
        }
        g2.drawLine((current.x/100.0).toInt(), (current.y/100.0).toInt(), (day.points.first().x/100.0).toInt(), (day.points.first().y/100.0).toInt())

        drawRect(Rectangle(5254, 50072, 94821, 66490), g2)
    }
}
fun main() {
    val frame = JFrame()
    val day = Day9(false,  readFullText("_2025/d9/input"))
    frame.contentPane = Canvas(day)
    frame.size = Dimension(400, 400)
    frame.layout = BorderLayout()
    frame.isVisible = true
}