package _2019

import util.get
import util.set

enum class OPCode(val value: Int, val jump: Int, val autoJump: Boolean = true) {
    ADD(1, 4), MULTIPLY(2, 4), INPUT(3, 2), OUTPUT(4, 2),
    JUMP_IF_TRUE(5, 3, false), JUMP_IF_FALSE(6, 3, false),
    LOWER_THAN(7, 4), EQUALS(8, 4), ADJUST_RELATIVE(9, 2), STOP(99, 1);

    companion object {
        fun from(code: Int): OPCode {
            return OPCode.values().first { it.value == code }
        }
    }
}

class IntCode(input: String, val setting: Long = 0L, var inputInt: Long = setting) {
    val data = input.split(",").map { it.toLong() }.toMutableList()
    private var address = 0L
    var output = mutableListOf<Long>()
    private var settingMode = true
    private var relativeIndex = 0L
    var halted = false

    /**
     * @return If the program has stopped
     */
    fun executeOneInstruction(): Boolean {
        val number = data[address].toString().let { it.padStart(5, '0') }
        val opCode = OPCode.from(number.takeLast(2).toInt())

        println(number)
        println(output)

        val param1 = getParam(number[2], 1)
        val param2 = getParam(number[1], 2)

        when (opCode) {
            OPCode.STOP -> {
                halted = true
                return true
            }

            else -> {
                when (opCode) {
                    OPCode.ADD -> data[data[address + 3]] = param1 + param2
                    OPCode.MULTIPLY -> data[data[address + 3]] = param1 * param2
                    OPCode.OUTPUT -> {
                        output.add(param1)
                        address = (address + opCode.jump) % data.size
                        return true
                    }

                    OPCode.INPUT -> {
                        data[data[address + 1]] = if (settingMode) setting else inputInt
                        if (settingMode) {
                            settingMode = false
                        }
                    }

                    OPCode.JUMP_IF_TRUE -> address = if (param1 != 0L) param2 else address + 3
                    OPCode.JUMP_IF_FALSE -> address = if (param1 == 0L) param2 else address + 3
                    OPCode.LOWER_THAN -> data[data[address + 3]] = if (param1 < param2) 1 else 0
                    OPCode.EQUALS -> data[data[address + 3]] = if (param1 == param2) 1 else 0
                    OPCode.ADJUST_RELATIVE -> relativeIndex += param1
                    else -> {
                        throw IllegalArgumentException("Unknown opcode $opCode")
                    }
                }
                if (opCode.autoJump) {
                    address = (address + opCode.jump) % data.size
                }
                return false
            }
        }
    }

    private fun getParam(mode: Char, offset: Int): Long = try {
        when (mode) {
            '0' -> data[data[address + offset]]
            '1' -> data[address + offset]
            '2' -> data[data[address + offset] + relativeIndex]
            else -> throw IllegalArgumentException("Unknown mode $mode")
        }
    } catch (e: Exception) {
        0L
    }

    fun executeUntilOutput(): Long {
        while (!executeOneInstruction());

        return output.last()
    }

    fun execute(): Long {
        while (!halted)
            while (!executeOneInstruction());
        return output.last()
    }
}
