package com.broszke.mathlete

public fun calculateFraction(a: Int, b: Int): String {
    if (b == 0) {
        throw IllegalArgumentException("Denominator cannot be zero.")
    }
    var sign = ""
    var nominator = a
    var denominator = b
    if (denominator < 0) {
        nominator *= -1
        denominator *= -1
    }
    if (nominator < 0) {
        nominator *= -1
        sign += "-"
    }
    return if (nominator % denominator == 0) {
        sign + (nominator / denominator).toString()
    } else {
        val divisor = gcd(nominator, denominator)
        nominator /= divisor
        denominator /= divisor
        formatFraction(sign, nominator, denominator)
    }
}

public fun formatFraction(sign: String, nominator: Int, denominator: Int): String {
    return if (nominator % denominator == 0) {
        "$sign${nominator / denominator}"
    } else if (nominator / denominator == 0) {
        "$sign\\frac{$nominator}{$denominator}"
    } else {
        "$sign${nominator/denominator}\\frac{${nominator % denominator}}{$denominator}"
    }
}

public fun gcd(a: Int, b: Int): Int {
    return if (b == 0) {
        a
    } else {
        gcd(b, a % b)
    }
}