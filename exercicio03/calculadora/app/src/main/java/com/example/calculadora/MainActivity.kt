package com.example.calculadora

import android.graphics.ColorSpace
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.Rgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.ui.theme.CalculadoraTheme
import kotlin.collections.plusAssign
import kotlin.div
import kotlin.times

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelaCalculadora()
        }
    }
}

@Composable
fun TelaCalculadora() {
    var num by remember { mutableStateOf("0") }
    var numAnterior by remember { mutableStateOf<Double?>(null) }
    var operacao by remember { mutableStateOf<String?>(null) }
    val botoes = listOf(
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("0", "C", "=", "+")
    )

    fun clicar(valor: String) {
        when (valor) {
            "+", "-", "*", "/" -> {
                numAnterior = num.toDouble()
                operacao = valor
                num = "0"
            }

            "=" -> {
                val atual = num.toDouble()
                val resultado = when (operacao) {
                    "+" -> numAnterior!! + atual
                    "-" -> numAnterior!! - atual
                    "*" -> numAnterior!! * atual
                    "/" -> numAnterior!! / atual
                    else -> atual
                }
                num = resultado.toString()
                numAnterior = null
                operacao = null
            }

            "C" -> {
                num = 0.toString()
                numAnterior = null
                operacao = null
            }

            else -> {
                num = if (num == "0") valor else num + valor
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
                .background(Color.Black),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .padding(25.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text("$num", color = Color.White, fontSize = 70.sp)
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.DarkGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(modifier = Modifier
                .padding(2.dp)) {

                botoes.forEach { linhaI ->
                    Row(
                        modifier = Modifier
                            .padding(2.dp)
                    ) {
                        linhaI.forEach { linhaJ ->
                            var pressionado by remember { mutableStateOf(false) }
                            val escala by animateFloatAsState(targetValue = if (pressionado) 0.85f else 1f)
                            Button(
                                onClick = {
                                    pressionado = true
                                    clicar(linhaJ)},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .weight(1f)
                                    .aspectRatio(0.7f)
                                    .scale(escala)
                            )
                            {Text(
                                text = "$linhaJ",
                                fontSize = 24.sp,
                                color = Color.White
                            )}
                            LaunchedEffect(pressionado) {
                                if (pressionado) {
                                    kotlinx.coroutines.delay(100)
                                    pressionado = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    TelaCalculadora()
}
