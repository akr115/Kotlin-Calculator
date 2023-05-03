package com.example.basic_calculator_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.basic_calculator_app.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var expressionSyntax = ""
    private var expressionSemantics = ""
    private var dot = true
    private lateinit var resultExp: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        update()
    }

    fun buNumberEvent(view: View) {
        val buSelect = view as Button
        when (buSelect.id) {
            binding.bu0.id -> {
                evaluateExpression("0")
            }
            binding.bu1.id -> {
                evaluateExpression("1")

            }
            binding.bu2.id -> {
                evaluateExpression("2")
            }
            binding.bu3.id -> {
                evaluateExpression("3")
            }
            binding.bu4.id -> {
                evaluateExpression("4")
            }
            binding.bu5.id -> {
                evaluateExpression("5")
            }
            binding.bu6.id -> {
                evaluateExpression("6")
            }
            binding.bu7.id -> {
                evaluateExpression("7")
            }
            binding.bu8.id -> {
                evaluateExpression("8")
            }
            binding.bu9.id -> {
                evaluateExpression("9")
            }
            binding.buDot.id -> {
                if (dot) {
                    if (expressionSyntax.isEmpty() || isOperator(expressionSyntax.last())) {
                        evaluateExpression("0.")
                    } else {
                        evaluateExpression(".")
                    }

                    dot = false
                }

            }
        }
    }

    fun buOpEvent(view: View) {
        val buSelect = view as Button
        when (buSelect.id) {
            binding.buMul.id -> {
                evaluateExpression("*")
            }
            binding.buDiv.id -> {
                evaluateExpression("/")
            }
            binding.buSub.id -> {
                evaluateExpression("-")
            }
            binding.buSum.id -> {
                evaluateExpression("+")
            }
            binding.buBack.id -> {
                evaluateExpression("")
            }
            binding.buPer.id -> {
                evaluateExpression("%")
            }
        }
        dot = true
    }

    fun buEqualEvent(view: View) {
        resultExp = ExpressionBuilder(expressionSemantics).build()
        try {
            val result = resultExp.evaluate()
            val longResult = result.toLong()
            expressionSyntax = if (result == longResult.toDouble()) {
                longResult.toString()
            } else {
                result.toString()
            }
            expressionSemantics = expressionSyntax
            update()
            val intResult = result.toInt()
            dot = intResult.toDouble() == result
        } catch (e: java.lang.Exception) {
            return
        }

    }

    fun buCleanEvent() {
        expressionSyntax = ""
        expressionSemantics = ""
        update()
    }

    private fun evaluateExpression(s: String) {
       if (s == "%"){
            expressionSemantics += "/100"
            expressionSyntax += "%"
        } else if (s != "") {
            expressionSyntax += s
            expressionSemantics += s
        }  else {
           expressionSemantics = if(expressionSyntax.takeLast(1) == "%"){
               expressionSemantics.dropLast(4)
           } else {
               expressionSemantics.dropLast(1)
           }
            expressionSyntax = expressionSyntax.dropLast(1)

        }
        update()
    }

    private fun update() {
        binding.etShowNumber.setText(expressionSyntax)
    }

    private fun isOperator(s: Char): Boolean {
        return (s == '+' || s == '-' || s == '*' || s == '/')
    }
}

