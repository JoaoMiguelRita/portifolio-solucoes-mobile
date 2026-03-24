package com.example.cacatesouro

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}


@Composable
fun App() {
    var timer by remember { mutableStateOf(0L) }
    val totalTime = remember { (System.currentTimeMillis() - timer) / 1000 }
    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = "/telaInicial"
    ) {
        composable("/telaTesouro") {
            val context = LocalContext.current

            DisposableEffect(Unit) {
                val mediaPlayer = MediaPlayer.create(context, R.raw.conglatulations)
                mediaPlayer?.start()

                onDispose {
                    mediaPlayer.release()
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row() {
                    Text("Parabéns você passou por todos os desafios!")
                }
                Row() {
                    Text("Seu tempo para concluir todas as perguntas foi de $totalTime segundos")
                }
                Button(onClick = { navigationController.navigate("/telaInicial") }) {
                    Text("jogar novamente!")
                }
            }
        }
        composable("/telaInicial") {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Caça ao Tesouro", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    timer = System.currentTimeMillis()
                    navigationController.navigate("/question01")
                }) {
                    Text(text = "Começar")
                }
            }
        }
        composable("/question01") {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Tela(
                    "Qual a capital do México?",
                    "Ciudad de Mexico",
                    pist = "Tem Mexico no nome",
                    onBack = { navigationController.popBackStack() },
                    onNext = { navigationController.navigate("/question02") }
                )
            }
        }
        composable("/question02") {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Tela(
                    "Quantos países formam a america do norte?",
                    "3",
                    pist = "São os países que vão sediar a copa do mundo de 2026",
                    onBack = { navigationController.popBackStack() },
                    onNext = { navigationController.navigate("/question03") }
                )
            }
        }
        composable("/question03") {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Tela(
                    "Em qual país ocorreu a primeira revolução industrial?",
                    "Inglaterra",
                    pist = "Nessa país as pessoas falam inglês",
                    onBack = { navigationController.popBackStack() },
                    onNext = { navigationController.navigate("/telaTesouro") }
                )
            }
        }
    }
}

@Composable
fun Tela (question: String,
          response: String,
          pist: String,
          onBack: () -> Unit,
          onNext: () -> Unit
){
    var textDigited by remember { mutableStateOf("") }
    var msgError by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        Text(text = "Pista: $pist", fontSize = 18.sp, color = Color.Green)
        Text(text = question, fontSize = 22.sp)

        OutlinedTextField(
            value = textDigited,
            onValueChange = {
                textDigited = it
                if (msgError.isNotEmpty()) msgError = ""
            },
            label = {Text("Digite sua resposta")},
            singleLine = true,
            keyboardOptions = androidx . compose . foundation . text . KeyboardOptions(
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        )
        )

        if (msgError.isNotEmpty()) Text(msgError, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(24.dp))

        Row() {
            Button(onClick = onBack){
                Text("voltar")
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = {
            if (textDigited.trim().lowercase() == response.lowercase()) {
                onNext()
            } else {
                msgError = "Resposta incorreta!"
                textDigited = ""
            }
        }) {
            Text("Enviar resposta")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Preview (){
    Tela(
        question = "Tela 01",
        response = "teste",
        pist = "teste",
        onBack = {},
        onNext = {},
    )
}