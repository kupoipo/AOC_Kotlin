package util
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.writeText

fun main() {
    for (i in 11..20) {
        Files.createDirectory(Paths.get("./src/main/kotlin/_2021/d$i"))
        Files.createFile(Paths.get("./src/main/kotlin/_2021/d$i/test.txt"))
        Files.createFile(Paths.get("./src/main/kotlin/_2021/d$i/input.txt"))
        var f = Files.createFile(Paths.get("./src/main/kotlin/_2021/d$i/Day$i.kt"))

        var str = "package _2021.d$i\n" +
                "\n" +
                "import util.Day\n" +
                "import util.readFullText\n" +
                "import kotlin.system.measureTimeMillis\n" +
                "class Day$i(override val input : String) : Day<Long>(input) {\n" +
                "    override fun solve1(): Long {\n" +
                "        return -1\n" +
                "    }\n" +
                "    override fun solve2(): Long {\n" +
                "        return -1\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "fun main() {\n" +
                "    //var day = Day$i(readFullText(\"_2021/d$i/test\"))\n" +
                "    var day = Day$i(readFullText(\"_2021/d$i/input\"))\n" +
                "\n" +
                "    val t1 = measureTimeMillis { println(\"Part 1 : \" + day.solve1()) }\n" +
                "    println(\"Temps partie 1 : {\$t1}\")\n" +
                "\n" +
                "    val t2 = measureTimeMillis { println(\"Part 2 : \" + day.solve2()) }\n" +
                "    println(\"Temps partie 2 : {\$t2}\")\n" +
                "}"

        f.writeText(str)
    }

}