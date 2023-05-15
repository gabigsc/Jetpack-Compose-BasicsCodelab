package com.example.basicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

// MainActivity é iniciada quando o usuário abre o app.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContent usado para definir o layout, mas, em vez de usar um arquivo XML, como você faria no sistema de visualização tradicional, é chamada funções de composição.
        setContent {
            // BasicsCodelabTheme é uma maneira de definir o estilo de funções de composição.
            BasicsCodelabTheme { //modifier - Os modificadores informam para um elemento da IU como serão dispostos, exibidos ou se comportarão no layout pai.
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

// As funções de composição podem ser usadas como qualquer outra função no Kotlin.
@Composable
////modifier - Os modificadores informam para um elemento da IU como serão dispostos, exibidos ou se comportarão no layout pai.
fun MyApp(modifier: Modifier = Modifier) {

    // shouldShowOnboarding está usando uma palavra-chave by em vez de =. É um delegado de propriedade que evita que você digite .value todas as vezes.
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    // Surface renderiza a cor do segundo plano.
    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (shouldShowOnboarding) {
            //É claro que precisamos compartilhar o estado que criamos em OnboardingScreen com a composição MyApp.
            //onContinueClicked a uma expressão lambda vazia significa "não fazer nada", o que é perfeito para uma visualização.
            //Quando o botão é clicado, shouldShowOnboarding é definido como false
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings() // Greeting essa função produzirá uma parte da hierarquia de IUs que exibe a entrada fornecida.
        }
    }
}

// As funções de composição podem ser usadas como qualquer outra função no Kotlin.
@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    ////modifier - Os modificadores informam para um elemento da IU como serão dispostos, exibidos ou se comportarão no layout pai.
    modifier: Modifier = Modifier
) {

    Column(
        ////modifier - Os modificadores informam para um elemento da IU como serão dispostos, exibidos ou se comportarão no layout pai.
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        //Button é uma composição fornecida pelo pacote do Material 3, que usa uma composição como o último argumento.
        Button(
            ////modifier - Os modificadores informam para um elemento da IU como serão dispostos, exibidos ou se comportarão no layout pai.
            modifier = Modifier.padding(vertical = 24.dp),
            //onClick é usado para mudar o estado do Button.
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

// As funções de composição podem ser usadas como qualquer outra função no Kotlin.
@Composable

private fun Greetings(
    //modifier - Os modificadores informam para um elemento da IU como serão dispostos, exibidos ou se comportarão no layout pai.
    modifier: Modifier = Modifier,
    // usando outro construtor de lista que permite definir o tamanho e preenchê-la com o valor contido na lambda ( $it representa o índice da lista)
    names: List<String> = List(1000) { "$it" }
) {
    //modifier - Os modificadores informam para um elemento da IU como serão dispostos, exibidos ou se comportarão no layout pai.
    //LazyColumn é usada exibir uma coluna rolável e renderizar somente os itens visíveis na tela, permitindo ganhos de desempenho ao renderizar uma lista grande.
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            // Greeting essa função produzirá uma parte da hierarquia de IUs que exibe a entrada fornecida.
            Greeting(name = name)
        }
    }
}

// As funções de composição podem ser usadas como qualquer outra função no Kotlin.
@Composable
// Greeting essa função produzirá uma parte da hierarquia de IUs que exibe a entrada fornecida.
//String. Text é uma função de composição fornecida pela biblioteca.
private fun Greeting(name: String) {
    Card(
        //CardDefaults.cardColors é usado para mudar as cores do Card.
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        ////modifier - Os modificadores informam para um elemento da IU como serão dispostos, exibidos ou se comportarão no layout pai.
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

// As funções de composição podem ser usadas como qualquer outra função no Kotlin.
@Composable
private fun CardContent(name: String) {

    //expanded é necessário armazenar um valor que indique se cada item está aberto ou não, como o estado do item.
    //remember é usado para proteger contra a recomposição, para que o estado não seja redefinido.
    //função mutableStateOf adiciona um estado interno a uma composição, que faz com que o Compose recomponha funções que leiam esse State.
    val expanded by remember { mutableStateOf(false) }

    Row(
        ////modifier - Os modificadores informam para um elemento da IU como serão dispostos, exibidos ou se comportarão no layout pai.
        modifier = Modifier
            // O modificador padding aplicará um pouco de espaço ao redor do elemento que ele decora.
            .padding(12.dp)
            // animateContentSize automatizará o processo de criação da animação, o que seria difícil de fazer manualmente.
            .animateContentSize(
                //animationSpec permite personalizar a animação.
                //spring as propriedades físicas (amortecimento e rigidez) dependem deles para tornar as animações mais naturais.
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        //A Column pode ser configurada para exibir o conteúdo no centro da tela.
        Column(
            // modifier - Os modificadores informam para um elemento da IU como serão dispostos, exibidos ou se comportarão no layout pai.
            modifier = Modifier
                // O modificador weight faz com que o elemento preencha todo o espaço disponível, tornando-o flexível, eliminando os outros elementos inflexíveis.
                .weight(1f)
                // O modificador padding aplicará um pouco de espaço ao redor do elemento que ele decora.
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                //A composição Text define um novo TextStyle. Você pode criar seu próprio TextStyle ou extrair um estilo definido pelo tema usando MaterialTheme.typography, que é preferencial.
                //Usando a função copy é possível modificar um estilo predefinido
                text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        //Substituir o botão por um ícone
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    //Mudando a visualização para emular a largura comum de um smartphone pequeno de 320 dp
    widthDp = 320,
    //Isso adiciona uma visualização no modo escuro.
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)

@Preview(showBackground = true, widthDp = 320)
// As funções de composição podem ser usadas como qualquer outra função no Kotlin.
@Composable
fun DefaultPreview() {
    // BasicsCodelabTheme é uma maneira de definir o estilo de funções de composição.
    BasicsCodelabTheme {
        // Greeting essa função produzirá uma parte da hierarquia de IUs que exibe a entrada fornecida.
        Greetings()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
// As funções de composição podem ser usadas como qualquer outra função no Kotlin.
@Composable
fun OnboardingPreview() {
    // BasicsCodelabTheme é uma maneira de definir o estilo de funções de composição.
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview
// As funções de composição podem ser usadas como qualquer outra função no Kotlin.
@Composable
fun MyAppPreview() {
    // BasicsCodelabTheme é uma maneira de definir o estilo de funções de composição.
    BasicsCodelabTheme {
        // modifier - Os modificadores informam para um elemento da IU como serão dispostos, exibidos ou se comportarão no layout pai.
        MyApp(Modifier.fillMaxSize())
    }
}
