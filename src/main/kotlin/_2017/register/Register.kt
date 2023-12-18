package _2017.register

import util.isInt
import java.lang.Exception

class Register {
    companion object {
        val register = mutableMapOf<String, Long>()

        fun inc(key: String, value: String) {
            register[key] = register.getOrPut(key) { 0 } + getValue(value)
        }

        fun dec(key: String, value: String) {
            register[key] = register.getOrPut(key) { 0 } - getValue(value)
        }

        fun getValue(str: String): Long {
            if (str.isInt()) return str.toLong()

            return register.getOrPut(str) { 0 }
        }

        fun compareTo(left: String, right: String, operand: String): Boolean {
            return when (operand){
                "==" -> getValue(left) == getValue(right)
                "<=" -> getValue(left) <= getValue(right)
                ">=" -> getValue(left) >= getValue(right)
                "!=" -> getValue(left) != getValue(right)
                "<" -> getValue(left) < getValue(right)
                ">" -> getValue(left) > getValue(right)
                else -> throw Exception("Operand $operand isn't supported yet")
            }
        }
    }
}