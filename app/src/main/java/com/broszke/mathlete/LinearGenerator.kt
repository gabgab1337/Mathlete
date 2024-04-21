package com.broszke.mathlete
import kotlin.random.Random
import com.broszke.mathlete.calculateFraction

data class LinearFunction(val slope: Int = 0, val yIntercept: Int = 0)
class LinearGenerator : QuestionGenerator{
    private var function: LinearFunction
    init{
        function = generateFunction()
    }
    private fun generateFunction(): LinearFunction {
        val random = Random
        val slope = random.nextInt(10) + 1
        val yIntercept = random.nextInt(30) - 15
        return LinearFunction(slope, yIntercept)
    }
    private fun calculateX(equals: Int = 0) : String {
        return calculateFraction(equals - function.yIntercept, function.slope)
    }
    private fun calculateWrongX(equals: Int = 0) : String{
        val random = Random
        var change = 0
        while (change == 0) {
            change = random.nextInt(10) - 10
        }
        return calculateFraction((equals - function.yIntercept + change), function.slope)
    }

    override fun generateQuestionX(): QuizQuestion {
        function = generateFunction()
        val question = "Oblicz x dla:\n"
        val random = Random
        val equals = random.nextInt(40) - 20
        var expression: String = when (function.slope) {
            1 -> {
                "x"
            }
            -1 -> {
                "-x"
            }
            else -> {
                "${function.slope}x"
            }
        }
        expression += if (function.yIntercept > 0) {
            " + ${function.yIntercept} = $equals"
        } else if (function.yIntercept < 0) {
            " - ${-function.yIntercept} = $equals"
        }else{
            " = $equals"
        }
        val correctAnswer = calculateX(equals)
        var wrongAnswer1 = calculateWrongX(equals)
        val wrongAnswer2 = calculateWrongX(equals)
        var wrongAnswer3 = calculateWrongX(equals)
        while (wrongAnswer1 == wrongAnswer2) {
            wrongAnswer1 = calculateWrongX(equals)
        }
        while (wrongAnswer3 == wrongAnswer1 || wrongAnswer3 == wrongAnswer2) {
            wrongAnswer3 = calculateWrongX(equals)
        }
        val answers = listOf(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3).shuffled()
        val correctAnswerIndex = answers.indexOf(correctAnswer)
        return QuizQuestion(question, expression, answers, correctAnswerIndex)
    }

    override fun generateQuestionPP(): QuizQuestion { // Perpendicular Parallel
        function = generateFunction()
        val random = Random
        val questionType = random.nextInt(2)
        // *** PERPENDICULAR *** //
        if (questionType == 0) {
            val question = "Wskaż funkcję prostopadłą dla:\n"
            var expression: String = when (function.slope) {
                1 -> {
                    "f(x) = x"
                }

                -1 -> {
                    "f(x) = -x"
                }

                else -> {
                    "f(x) = ${function.slope}x"
                }
            }
            expression += if (function.yIntercept > 0) {
                " + ${function.yIntercept}"
            } else if (function.yIntercept < 0) {
                " - ${-function.yIntercept}"
            } else {
                ""
            }

            val perpendicularSlope = -1 / function.slope.toFloat()
            val perpendicularYIntercept = random.nextInt(30) - 15
            var correctAnswer:String = when (perpendicularSlope) {
                1F -> {
                    "f(x) = x"
                }

                -1F -> {
                    "f(x) = -x"
                }

                else -> {
                    "f(x) = ${perpendicularSlope}x"
                }
            }
            correctAnswer += if (perpendicularYIntercept > 0) {
                " + ${perpendicularYIntercept}"
            } else if (perpendicularYIntercept < 0) {
                " - ${-perpendicularYIntercept}"
            } else {
                ""
            }

            val wrongAnswer1YIntercept = function.yIntercept + random.nextInt(10) + 1
            var wrongAnswer1:String = when (function.slope) {
                1 -> {
                    "f(x) = x"
                }

                -1 -> {
                    "f(x) = -x"
                }

                else -> {
                    "f(x) = ${function.slope}x"
                }
            }
            wrongAnswer1 += if (wrongAnswer1YIntercept > 0) {
                " + ${wrongAnswer1YIntercept}"
            } else if (wrongAnswer1YIntercept < 0) {
                " - ${-wrongAnswer1YIntercept}"
            } else {
                ""
            }

            val wrongAnswer2Slope = function.slope + random.nextInt(10) + 1
            val wrongAnswer2YIntercept = function.yIntercept - random.nextInt(10) - 1
            var wrongAnswer2:String = when (wrongAnswer2Slope) {
                1 -> {
                    "f(x) = x"
                }

                -1 -> {
                    "f(x) = -x"
                }

                else -> {
                    "f(x) = ${wrongAnswer2Slope}x"
                }
            }
            wrongAnswer2 += if (wrongAnswer2YIntercept > 0) {
                " + ${wrongAnswer2YIntercept}"
            } else if (wrongAnswer2YIntercept < 0) {
                " - ${-wrongAnswer2YIntercept}"
            } else {
                ""
            }

            val wrongAnswer3Slope = function.slope - random.nextInt(10) - 1
            val wrongAnswer3YIntercept = function.yIntercept + random.nextInt(10) + 1
            var wrongAnswer3:String = when (wrongAnswer3Slope) {
                1 -> {
                    "f(x) = x"
                }

                -1 -> {
                    "f(x) = -x"
                }

                else -> {
                    "f(x) = ${wrongAnswer3Slope}x"
                }
            }
            wrongAnswer3 += if (wrongAnswer3YIntercept > 0) {
                " + ${wrongAnswer3YIntercept}"
            } else if (wrongAnswer3YIntercept < 0) {
                " - ${-wrongAnswer3YIntercept}"
            } else {
                ""
            }

            val answers = listOf(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3).shuffled()
            val correctAnswerIndex = answers.indexOf(correctAnswer)
            return QuizQuestion(question, expression, answers, correctAnswerIndex)
        }
        // *** PARALLEL *** //
        else {
            val question = "Wskaż funkcję równoległą dla:\n"
            var expression: String = when (function.slope) {
                1 -> {
                    "f(x) = x"
                }

                -1 -> {
                    "f(x) = -x"
                }

                else -> {
                    "f(x) = ${function.slope}x"
                }
            }
            expression += if (function.yIntercept > 0) {
                " + ${function.yIntercept}"
            } else if (function.yIntercept < 0) {
                " - ${-function.yIntercept}"
            } else {
                ""
            }

            val parallelSlope = function.slope
            val parallelYIntercept = function.yIntercept + random.nextInt(10) + 1
            var correctAnswer:String = when (parallelSlope) {
                1 -> {
                    "f(x) = x"
                }

                -1 -> {
                    "f(x) = -x"
                }

                else -> {
                    "f(x) = ${parallelSlope}x"
                }
            }
            correctAnswer += if (parallelYIntercept > 0) {
                " + ${parallelYIntercept}"
            } else if (parallelYIntercept < 0) {
                " - ${-parallelYIntercept}"
            } else {
                ""
            }

            val wrongAnswer1Slope = -(1 / function.slope.toFloat())
            val wrongAnswer1YIntercept = random.nextInt(30) - 15
            var wrongAnswer1:String = when (wrongAnswer1Slope) {
                1F -> {
                    "f(x) = x"
                }

                -1F -> {
                    "f(x) = -x"
                }

                else -> {
                    "f(x) = ${wrongAnswer1Slope}x"
                }
            }
            wrongAnswer1 += if (wrongAnswer1YIntercept > 0) {
                " + ${wrongAnswer1YIntercept}"
            } else if (wrongAnswer1YIntercept < 0) {
                " - ${-wrongAnswer1YIntercept}"
            } else {
                ""
            }

            val wrongAnswer2Slope = function.slope + random.nextInt(10) + 1
            val wrongAnswer2YIntercept = function.yIntercept - random.nextInt(10) - 1
            var wrongAnswer2:String = when (wrongAnswer2Slope) {
                1 -> {
                    "f(x) = x"
                }

                -1 -> {
                    "f(x) = -x"
                }

                else -> {
                    "f(x) = ${wrongAnswer2Slope}x"
                }
            }
            wrongAnswer2 += if (wrongAnswer2YIntercept > 0) {
                " + ${wrongAnswer2YIntercept}"
            } else if (wrongAnswer2YIntercept < 0) {
                " - ${-wrongAnswer2YIntercept}"
            } else {
                ""
            }

            val wrongAnswer3Slope = function.slope - random.nextInt(10) - 1
            val wrongAnswer3YIntercept = function.yIntercept + random.nextInt(10) + 1
            var wrongAnswer3:String = when (wrongAnswer3Slope) {
                1 -> {
                    "f(x) = x"
                }

                -1 -> {
                    "f(x) = -x"
                }

                else -> {
                    "f(x) = ${wrongAnswer3Slope}x"
                }
            }
            wrongAnswer3 += if (wrongAnswer3YIntercept > 0) {
                " + ${wrongAnswer3YIntercept}"
            } else if (wrongAnswer3YIntercept < 0) {
                " - ${-wrongAnswer3YIntercept}"
            } else {
                ""
            }

            val answers = listOf(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3).shuffled()
            val correctAnswerIndex = answers.indexOf(correctAnswer)
            return QuizQuestion(question, expression, answers, correctAnswerIndex)
        }
    }
}