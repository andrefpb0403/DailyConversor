package com.example.dailyconversor

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class ConversorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_conversor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val conversorTitle = findViewById<TextView>(R.id.tv_conversor_title)
        val conversorSubtitle = findViewById<TextView>(R.id.tv_conversor_subtitle)
        val tvResult = findViewById<TextView>(R.id.tv_conversor_result)
        val tieWeight = findViewById<TextInputEditText>(R.id.tie_weight)
        val tieDistance = findViewById<TextInputEditText>(R.id.tie_distance)
        val tieVolume = findViewById<TextInputEditText>(R.id.tie_volume)
        val btnErase = findViewById<Button>(R.id.btn_erase_data)
        val btnResult = findViewById<Button>(R.id.btn_conversion)

        val spinnerWeight1: Spinner = findViewById(R.id.spinner_weight_1)
        val spinnerWeight2: Spinner = findViewById(R.id.spinner_weight_2)
        val spinnerDistance1: Spinner = findViewById(R.id.spinner_distance_1)
        val spinnerDistance2: Spinner = findViewById(R.id.spinner_distance_2)
        val spinnerVolume1: Spinner = findViewById(R.id.spinner_volume_1)
        val spinnerVolume2: Spinner = findViewById(R.id.spinner_volume_2)

        ArrayAdapter.createFromResource(
            this,
            R.array.weight_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerWeight1.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.weight_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerWeight2.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.distance_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDistance1.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.distance_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDistance2.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.volume_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerVolume1.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.volume_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerVolume2.adapter = adapter

        }

        btnResult.setOnClickListener {
            val inputWeight = tieWeight.text.toString()
            val inputDistance = tieDistance.text.toString()
            val inputVolume = tieVolume.text.toString()

            if (inputWeight.isNotEmpty()) {
                val fromWeightUnit = spinnerWeight1.selectedItem.toString()
                val toWeightUnit = spinnerWeight2.selectedItem.toString()
                val weightValue = inputWeight.toDouble()
                val weightResult = convertWeight(weightValue, fromWeightUnit, toWeightUnit)
                tvResult.text = "$weightResult ${spinnerWeight2.selectedItem}"
            }
            if (inputDistance.isNotEmpty()) {
                val fromDistanceUnit = spinnerDistance1.selectedItem.toString()
                val toDistanceUnit = spinnerDistance2.selectedItem.toString()
                val distanceValue = inputDistance.toDouble()
                val distanceResult = convertDistance(distanceValue, fromDistanceUnit, toDistanceUnit)
                tvResult.text = "$distanceResult ${spinnerDistance2.selectedItem}"
            }
            if (inputVolume.isNotEmpty()) {
                val fromVolumeUnit = spinnerVolume1.selectedItem.toString()
                val toVolumeUnit = spinnerVolume2.selectedItem.toString()
                val volumeValue = inputVolume.toDouble()
                val volumeResult = convertVolume(volumeValue, fromVolumeUnit, toVolumeUnit)
                tvResult.text = "$volumeResult ${spinnerVolume2.selectedItem}"
            }
        }

        btnErase.setOnClickListener {

            tieWeight.setText("")
            tieDistance.setText("")
            tieVolume.setText("")

            spinnerWeight1.setSelection(0)
            spinnerWeight2.setSelection(0)
            spinnerDistance1.setSelection(0)
            spinnerDistance2.setSelection(0)
            spinnerVolume1.setSelection(0)
            spinnerVolume2.setSelection(0)

            tvResult.text = ""
        }
    }

    private fun convertWeight(value: Double, fromUnit: String, toUnit: String): Double {
        // Primeiro, converte para a unidade base (quilograma)
        val baseValue = when (fromUnit) {
            "Quilogramas" -> value
            "Gramas" -> value / 1000
            "Miligramas" -> value / 1_000_000
            "Toneladas" -> value * 1000
            "Libras" -> value / 2.20462
            "Onças" -> value / 35.274
            else -> value
        }

        // Agora converte de quilograma para a unidade de destino
        return when (toUnit) {
            "Quilogramas" -> baseValue
            "Gramas" -> baseValue * 1000
            "Miligramas" -> baseValue * 1_000_000
            "Toneladas" -> baseValue / 1000
            "Libras" -> baseValue * 2.20462
            "Onças" -> baseValue * 35.274
            else -> baseValue
        }
    }

    private fun convertDistance(value: Double, fromUnit: String, toUnit: String): Double {
        // Primeiro, converte para a unidade base (metro)
        val baseValueInMeters = when (fromUnit) {
            "Metros" -> value
            "Centímetros" -> value / 100
            "Milímetros" -> value / 1000
            "Quilômetros" -> value * 1000
            "Milhas" -> value * 1609.34
            "Jardas" -> value / 1.09361
            "Pés" -> value / 3.28084
            "Polegada" -> value / 39.3701
            else -> value
        }

        // Agora converte de metros para a unidade de destino
        return when (toUnit) {
            "Metros" -> baseValueInMeters
            "Centímetros" -> baseValueInMeters * 100
            "Milímetros" -> baseValueInMeters * 1000
            "Quilômetros" -> baseValueInMeters / 1000
            "Milhas" -> baseValueInMeters / 1609.34
            "Jardas" -> baseValueInMeters * 1.09361
            "Pés" -> baseValueInMeters * 3.28084
            "Polegada" -> baseValueInMeters * 39.3701
            else -> baseValueInMeters
        }
    }


    private fun convertVolume(value: Double, fromUnit: String, toUnit: String): Double {
        // Primeiro, converte para a unidade base (litro)
        val baseValueInLiters = when (fromUnit) {
            "Litros" -> value
            "Mililitros" -> value / 1000
            "Metros cúbicos" -> value * 1000
            "Centímetros cúbicos" -> value / 1000
            "Galões" -> value * 3.78541
            "Onças fluídas" -> value / 33.814
            "Barris" -> value * 158.987
            else -> value
        }

        // Agora converte de litros para a unidade de destino
        return when (toUnit) {
            "Litros" -> baseValueInLiters
            "Mililitros" -> baseValueInLiters * 1000
            "Metros cúbicos" -> baseValueInLiters / 1000
            "Centímetros cúbicos" -> baseValueInLiters * 1000
            "Galões" -> baseValueInLiters / 3.78541
            "Onças fluídas" -> baseValueInLiters * 33.814
            "Barris" -> baseValueInLiters / 158.987
            else -> baseValueInLiters
        }
    }
}