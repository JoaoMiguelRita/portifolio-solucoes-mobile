package com.example.pedrapapeltesoura

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // O app começa aqui
        enableEdgeToEdge() // habilita de borda a borda da tela
        setContent { // espera receber conteudo para a tela
            TelaHome()
        }
    }

    @Composable
    fun TelaHome() {
        var result by remember { mutableStateOf("") }
        val opcoes = listOf(
            R.drawable.pedra,
            R.drawable.papel,
            R.drawable.tesoura
        )
        val randomOpion = opcoes.random()
        var imagem by remember { mutableStateOf<Int?>(null) }
        var tempo by remember { mutableStateOf(3) }
        val timer = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tempo = (millisUntilFinished / 1000).toInt()
            }
            override fun onFinish() {
                tempo = 0
            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center)
            {Text("Vamos jogar pedra, papel, tesoura?")}
            Button(onClick = {
                timer.start()
                imagem = randomOpion
            }) {
                Text("Jogar")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                if (tempo == 0 && imagem != null) {
                    Text("$result")
                    imagem?.let {
                        Image(
                            painter = painterResource(id = it),
                            contentDescription = "Escolha",
                            modifier = Modifier.size(150.dp)
                        )
                    }
                } else if (imagem != null) {
                    Text("$tempo")
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewHome() {
        TelaHome()
    }
}