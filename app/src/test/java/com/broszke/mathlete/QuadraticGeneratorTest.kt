package com.broszke.mathlete

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class QuadraticGeneratorTest {

    @Test
    fun testCalculateX() {
        val generator = QuadraticGenerator()

        // Test when delta is negative
        generator.function = QuadraticFunction(a = 1, b = 1, c = 1, delta = -1)
        assertEquals("\\text{Brak miejsc zerowych}", generator.calculateX())

        // Test when delta is zero
        generator.function = QuadraticFunction(a = 1, b = 2, c = 1, delta = 0)
        assertEquals("x_{0} = -1", generator.calculateX())

        // Test when delta is a perfect square and a, b, and c are positive
        generator.function = QuadraticFunction(a = 1, b = 2, c = 1, delta = 4)
        assertEquals("x_{1} = -2\\;\\;\\;x_{2} = 0", generator.calculateX())

        // Test when delta is a perfect square and a is positive, b and c are negative
        generator.function = QuadraticFunction(a = 1, b = -2, c = -1, delta = 4)
        assertEquals("x_{1} = 0\\;\\;\\;x_{2} = 2", generator.calculateX())

        generator.function = QuadraticFunction(a = 1, b = 0, c = -25, delta = 100)
        assertEquals("x_{1} = -5\\;\\;\\;x_{2} = 5", generator.calculateX())

        // Test when delta is a large perfect square and a, b, and c are negative
        generator.function = QuadraticFunction(a = -1, b = -2, c = -1, delta = 100)
        assertEquals("x_{1} = -10\\;\\;\\;x_{2} = 12", generator.calculateX())

        // Test when delta is a large perfect square and a is negative, b and c are positive
        generator.function = QuadraticFunction(a = -1, b = 2, c = 1, delta = 100)
        assertEquals("x_{1} = -12\\;\\;\\;x_{2} = 10", generator.calculateX())

        // Test when delta is a large perfect square and a is positive, b and c are negative
        generator.function = QuadraticFunction(a = 1, b = -2, c = -1, delta = 100)
        assertEquals("x_{1} = -10\\;\\;\\;x_{2} = 12", generator.calculateX())
        // Test when delta is a perfect square and a, b, and c are negative
        generator.function = QuadraticFunction(a = -1, b = -2, c = -1, delta = 4)
        assertEquals("x_{1} = 0\\;\\;\\;x_{2} = 2", generator.calculateX())

        // Test when delta is a perfect square and a is negative, b and c are positive
        generator.function = QuadraticFunction(a = -1, b = 2, c = 1, delta = 4)
        assertEquals("x_{1} = -2\\;\\;\\;x_{2} = 0", generator.calculateX())
    }

    @Test
    fun testGenerateFunction() {
        val generator = QuadraticGenerator()

        for (i in 1..100) {
            val function = generator.generateFunction()

            // Check if delta is a perfect square
            val sqrtDelta = kotlin.math.sqrt(function.delta.toDouble())
            assertTrue(sqrtDelta == kotlin.math.floor(sqrtDelta))

            // Check if a * c is not zero
            assertNotEquals(0, function.a * function.c)
        }
    }
}