package com.broszke.mathlete

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class QuadraticGeneratorTest {

    @Test
    fun testGenerateFunction() {
        val generator = QuadraticGenerator()

        for (i in 1..10000) {
            val function = generator.generateFunction()

            val sqrtDelta = kotlin.math.sqrt(function.delta.toDouble())
            //assertTrue(sqrtDelta == kotlin.math.floor(sqrtDelta))

            //assertNotEquals(0, function.a * function.c)
        }
    }
}