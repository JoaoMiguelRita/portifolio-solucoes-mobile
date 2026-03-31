# 📝 ToDoList Compose

Aplicação Android de lista de tarefas desenvolvida com **Jetpack Compose** e arquitetura **MVVM**, com persistência local via **Room** e sincronização remota via **Retrofit**.

---

## 📱 Funcionalidades

- Visualizar lista de tarefas
- Adicionar nova tarefa com título e descrição
- Marcar/desmarcar tarefa como concluída
- Persistência local com banco SQLite (Room)
- Sincronização com API REST (Retrofit)

---

## 🏗️ Arquitetura

O projeto segue o padrão **MVVM + Repository**, recomendado pelo Google para aplicações Android modernas.

```
UI (Compose)
    ↕ eventos / estado
ViewModel
    ↕
Repository
    ↕               ↕
Room (local)    Retrofit (API)
```

### Camadas

| Camada | Responsabilidade |
|---|---|
| `UI` | Renderizar estado e emitir eventos do usuário |
| `ViewModel` | Gerenciar estado da tela e chamar o Repository |
| `Repository` | Coordenar fonte local e remota de dados |
| `Room` | Persistência offline no SQLite |
| `Retrofit` | Sincronização com servidor via API REST |

---

## 🗂️ Estrutura de arquivos

```
app/src/main/java/br/edu/satc/todolistcompose/
├── data/
│   ├── TaskData.kt          # Modelo de dados (@Entity Room)
│   ├── TaskDao.kt           # Interface de acesso ao banco (Room)
│   ├── TaskDatabase.kt      # Configuração do banco Room
│   ├── TaskRepository.kt    # Coordena Room + Retrofit
│   └── TaskApiService.kt    # Interface da API REST (Retrofit)
├── ui/
│   ├── components/
│   │   └── TaskCard.kt      # Componente visual de cada tarefa
│   ├── screens/
│   │   └── HomeScreen.kt    # Tela principal
│   ├── theme/               # Tema do app (cores, tipografia)
│   ├── TaskViewModel.kt     # ViewModel da tela principal
│   └── TaskScreen.kt        # Tela conectada ao ViewModel
└── MainActivity.kt          # Entry point da aplicação
```

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Kotlin | 2.1.0 | Linguagem principal |
| Jetpack Compose | BOM 2024.x | Interface declarativa |
| Room | 2.6.1 | Banco de dados local |
| Retrofit | 2.9.0 | Requisições HTTP |
| ViewModel | 2.7.0 | Gerenciamento de estado |
| Coroutines | 1.7.3 | Operações assíncronas |
| KSP | — | Geração de código (Room) |

---

## ⚙️ Configuração

### Pré-requisitos

- Android Studio Hedgehog ou superior
- JDK 17+
- Android SDK 24+

### Dependências (`build.gradle` — módulo app)

```kotlin
dependencies {
    // Compose
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")

    // ViewModel + Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
```

### Configurar a URL da API

Em `TaskApiService.kt`, substitua a URL base pela URL do seu servidor:

```kotlin
private const val BASE_URL = "https://sua-api.com/"
```

---

## 🔄 Fluxo de dados

1. O usuário interage com um `TaskCard` ou `HomeScreen`
2. O evento é repassado ao `TaskViewModel` via callback
3. O `ViewModel` chama o método correspondente no `TaskRepository`
4. O `Repository` salva primeiro no **Room** (garante persistência offline)
5. Em seguida, tenta sincronizar com a **API REST** via Retrofit
6. O `Flow` do Room emite a lista atualizada automaticamente
7. O `StateFlow` no `ViewModel` propaga o novo estado para a UI

---

## 📌 Decisões de projeto

**Offline-first:** o banco local é sempre a fonte da verdade. A API é sincronizada em segundo plano e falhas de rede não afetam a experiência do usuário.

**Separação de responsabilidades:** o `TaskCard` não conhece o `ViewModel` — ele apenas emite eventos via callbacks. A tela (`HomeScreen`) é responsável por conectar os dois.

**StateFlow sobre LiveData:** o `StateFlow` é mais adequado com Coroutines e Compose, eliminando a dependência do ciclo de vida do `LifecycleOwner`.

---

## 📚 Referências

- [Documentação oficial do Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Guia de arquitetura Android](https://developer.android.com/topic/architecture)
- [Room — documentação oficial](https://developer.android.com/training/data-storage/room)
- [Retrofit](https://square.github.io/retrofit/)
