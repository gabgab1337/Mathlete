package com.example.mathlete
import kotlin.random.Random

data class LinearFunction(val slope: Int, val yIntercept: Int)
class LinearGenerator {
    val function: LinearFunction
    init{
        function = generateFunction()
    }
    private fun generateFunction(): LinearFunction {
        val random = Random
        val slope = random.nextInt(10) + 1 // Avoid zero slope
        val yIntercept = random.nextInt(10)
        return LinearFunction(slope, yIntercept)
    }
    private fun calculateX() : Float{
        return -function.yIntercept.toFloat() / function.slope
    }
    private fun calculateWrongX() : Float{
        val random = Random
        var change = 0
        while (change == 0) {
            change = random.nextInt(10) - 5
        }
        return calculateX() + change
    }
}