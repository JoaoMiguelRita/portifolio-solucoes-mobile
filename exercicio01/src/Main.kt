import java.lang.Character.*

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val listaDeRegras = listOf(
        Requisito("Minímo 5 caracteres") { it.length >= 5 },
        Requisito("Deve possuir o ano de hexa (2026)") { it.contains("2026") },
        Requisito("Deve ter um emoji no primeiro caracter da senha") { getType(it[0]) == SURROGATE.toInt()},
        Requisito("Deve conter a inicial de uma das 7 maravilhas do mundo") { it.contains("t") || it.contains("c") || it.contains("m") || it.contains("p") || it.contains("m") || it.contains("c")},
        Requisito("Hehe dê 3 pulinhos") { it.contains("   ") },
    )

    var senhaAprovada = false

    do {
        println("\nDigite sua senha: ")
        val senhaDigitada = readLine() ?: "Precisa informar uma senha!"

        var erroEncontrado: String? = null

        for (regra in listaDeRegras){
            if (!regra.validar(senhaDigitada)) {
                erroEncontrado = regra.mensagem
                break
            }
        }

        if (erroEncontrado != null) {
            println("Erro: $erroEncontrado")
        } else {
            println("Parabéns, você acessou!")
            senhaAprovada = true
        }
    } while (!senhaAprovada)
}

class Requisito(
    var mensagem: String,
    var regra: (String) -> Boolean
){
    fun validar(senha: String): Boolean {
        return regra(senha)
    }
}