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

class IntCode(
    input: String,
    val setting: Long = 0L,
    var inputInt: Long = setting,
    val freeInputMode: Boolean = false,
    val beforeInput: () -> Unit = {}
) {
    val data = input.split(",").map { it.toLong() }.toMutableList().let { list ->
        repeat(4000) {
            list.add(0L)
        }
        list
    }
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


        val param1 = getParam(number[2], 1)
        val param2 = getParam(number[1], 2)


        when (opCode) {
            OPCode.STOP -> {
                halted = true
                return true
            }

            else -> {
                when (opCode) {
                    OPCode.ADD -> setParam(number[0], 3, param1 + param2)
                    OPCode.MULTIPLY -> setParam(number[0], 3, param1 * param2)
                    OPCode.OUTPUT -> {
                        output.add(param1)
                        address = (address + opCode.jump) % data.size
                        return true
                    }

                    OPCode.INPUT -> {
                        if (freeInputMode) {
                            beforeInput()
                            println("Input : ")
                            setParam(number[2], 1, readln().toLong())
                            /*
                            For the vaccum day
                            setParam(number[2], 1, readln().let { if (it.isEmpty()) 10 else {
                                if (it in "yn") it.first().code.toLong()
                                else it.uppercase().first().code.toLong()
                            } })*/
                        } else {
                            beforeInput()
                            setParam(number[2], 1, if (settingMode) setting else inputInt)
                            if (settingMode) {
                                settingMode = false
                            }
                        }
                    }

                    OPCode.JUMP_IF_TRUE -> address = if (param1 != 0L) param2 else address + 3
                    OPCode.JUMP_IF_FALSE -> address = if (param1 == 0L) param2 else address + 3
                    OPCode.LOWER_THAN -> setParam(number[0], 3, if (param1 < param2) 1 else 0)
                    OPCode.EQUALS -> setParam(number[0], 3, if (param1 == param2) 1 else 0)
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

    private fun setParam(mode: Char, offset: Int, value: Long) {
        when (mode) {
            '0' -> data[data[address + offset]] = value
            '2' -> data[data[address + offset] + relativeIndex] = value
            else -> throw IllegalArgumentException("Unknown mode $mode")
        }
    }

    fun executeUntilOutput(): Long {
        while (!executeOneInstruction());

        return output.last()
    }

    fun execute(withOutput: Boolean = false, transform: (Long) -> String = { it.toInt().toChar().toString() }): Long {
        while (!halted) {
            val v = executeUntilOutput()
            if (withOutput) print(transform(v))
        }
        return output.last()
    }

    fun running(): Boolean = !halted
}

