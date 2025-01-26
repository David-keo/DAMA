package com.example.gradescalculator

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private var currentComputo = 1
    private val gradesMap = mutableMapOf<Int, List<Double>>()//Lista para almacenar datos según computo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)//Para evitar el modo oscuro
        setContentView(R.layout.activity_main)

        val studentNameEditText = findViewById<EditText>(R.id.et_student_name)
        val minGradeEditText = findViewById<EditText>(R.id.et_min_grade)
        val nota1EditText = findViewById<EditText>(R.id.nota1)
        val nota2EditText = findViewById<EditText>(R.id.nota2)
        val nota3EditText = findViewById<EditText>(R.id.nota3)
        val calcularButton = findViewById<Button>(R.id.calcularButton)
        val errorTxtView = findViewById<TextView>(R.id.errorMessage)
        val computoGroup = findViewById<RadioGroup>(R.id.rg_computo)

        computoGroup.setOnCheckedChangeListener { _, checkedId -> //Listener para el comupto
            currentComputo = when (checkedId) {
                R.id.rb_computo1 -> 1
                R.id.rb_computo2 -> 2
                R.id.rb_computo3 -> 3
                else -> 1
            }
        }

        calcularButton.setOnClickListener {
            //Conversiones de datos
            val studentName = studentNameEditText.text.toString()
            val minGrade = minGradeEditText.text.toString().toDoubleOrNull()
            val nota1 = nota1EditText.text.toString().toDoubleOrNull()
            val nota2 = nota2EditText.text.toString().toDoubleOrNull()
            val nota3 = nota3EditText.text.toString().toDoubleOrNull()

            if (studentName.isNotEmpty() && minGrade != null && nota1 != null && nota2 != null && nota3 != null) { //Validación de campos
                val promedio = (nota1 + nota2 + nota3) / 3//Calculo del promedio
                gradesMap[currentComputo] = listOf(nota1, nota2, nota3)

                //para llevar los datos a la nueva actiivty
                val intent = Intent(this, FinalResultActivity::class.java)
                intent.putExtra("studentName", studentName)
                intent.putExtra("finalAverage", promedio)
                intent.putExtra("minimumPassingGrade", minGrade)
                startActivity(intent)

            } else {
                errorTxtView.text = "¡FALTAN CAMPOS POR COMPLETAR!"
                errorTxtView.setTextColor(Color.RED)
                errorTxtView.visibility = View.VISIBLE
            }
        }
    }
}
