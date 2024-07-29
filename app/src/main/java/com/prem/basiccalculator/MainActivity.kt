package com.prem.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.prem.basiccalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }

    fun onAllClearClick(view: View) {
        binding.textViewInputNumbers.text = ""
        binding.textViewOutputNumbers.text = ""
        stateError = false
        lastNumeric = false
    }

    fun onEqualClick(view: View) {
        onEqual()
        binding.textViewInputNumbers.text = binding.textViewOutputNumbers.text.toString().drop(1)
        binding.textViewOutputNumbers.text = ""
    }

    fun onDigitClick(view: View) {
        if (stateError) {
            binding.textViewInputNumbers.text = (view as Button).text
            stateError = false
        } else {
            binding.textViewInputNumbers.append((view as Button).text)
        }
        lastNumeric = true
        onEqual()
    }

    fun onOperatorClick(view: View){
        if (!stateError && lastNumeric){
            binding.textViewInputNumbers.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            onEqual()
        }
    }

    fun onBackClick(view: View){
        binding.textViewInputNumbers.text = binding.textViewInputNumbers.text.toString().dropLast(1)

        try {
            val lastchar = binding.textViewInputNumbers.text.toString().last()

            if (lastchar.isDigit()){
                onEqual()
            }
        } catch (e : Exception){
            binding.textViewOutputNumbers.text = ""
        }
    }

    private fun onEqual(){
        if(lastNumeric && !stateError){
            var txt = binding.textViewInputNumbers.text.toString()
            expression = ExpressionBuilder(txt).build()

            try{
                var result = expression.evaluate()
                binding.textViewOutputNumbers.text = "= "+result.toString()
            }
            catch (ex: Exception){
                binding.textViewOutputNumbers.text = "Error"
                stateError = true
            }
        }
    }
}