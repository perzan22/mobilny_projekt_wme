package com.example.projekt_amw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projekt_amw.ui.theme.Projekt_amwTheme

class MainActivity : ComponentActivity() {

    // zmienna przechowująca instancję klasy DataBaseHelper z funkcjami bazy danych
    private lateinit var databseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        databseHelper = DatabaseHelper(this)
        setContent {
            // Głównym komponentem jest motyw z Material3
            Projekt_amwTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Obsługę nawigacji po aplikacji umożliwia navController
                    val navController = rememberNavController()
                    MainScreen(navController, databseHelper)
                }
            }
        }
    }
}

// Komponent MainScreen do nawigacji po widokach
@Composable
fun MainScreen(navController: NavHostController, databaseHelper: DatabaseHelper) {
    NavHost(navController, startDestination = "home") {

        // Każdy widok otwiera się po przekazaniu odpowiedniej ścieżki do navHost
        composable("home") { MainPage(navController) }
        composable("aktualnosci") { Aktualnosci(navController, databaseHelper) }
        composable("kierunki") { Kierunki(navController, databaseHelper) }
        // Pobranie ID kierunku ze ścieżki z NavHost
        composable("kierunek_details/{kierunekID}") { backStackEntry ->
            val kierunekID = backStackEntry.arguments?.getString("kierunekID") ?: return@composable
            Kierunek_Details(navController = navController, databaseHelper = databaseHelper, kierunekID = kierunekID)
        }
        composable("kierunek_register/{kierunekID}") { backStackEntry ->
            val kierunekID = backStackEntry.arguments?.getString("kierunekID") ?: return@composable
            Kierunek_Register(navController = navController, databaseHelper = databaseHelper, kierunekID = kierunekID)
        }
    }
}

// Kokmponent ze stroną główną aplikacji
@Composable
fun MainPage(navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.zlogo_amw_biale_tlo), contentDescription = "Logo WME")
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Wydział mechaniczno-elektryczny AMW",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Zapraszamy do zapoznania się z najnowszymi aktualnościami naszego wydziału, a także do zapisania się na nasze kierunki studiów",
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )


        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Aktualności",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
            )
        // Przycisk przenosi do widoku aktualnosci za pomocą navController
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { navController.navigate("aktualnosci") }) {
            Text(text = "Zobacz aktualności")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Kierunki studiów",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        // Przycisk przenosi do widoku kierunki za pomocą navController
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { navController.navigate("kierunki") }) {
            Text(text = "Zobacz kierunki")
        }

    }

}

// Komponent pokazujący wszystkie aktualności
@Composable
fun Aktualnosci(navController: NavHostController, databaseHelper: DatabaseHelper) {

    // Pobranie listy aktualności z bazy danych
    val newsList = remember {
        databaseHelper.getAllNews()
    }

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Aktualności WME",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(30.dp))

        // Lista aktualności wyświetlana jest za pomocą pętli forEach
        newsList.forEach { newsItem ->
            Surface(modifier = Modifier
                .border(
                    2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    MaterialTheme.shapes.medium
                )
                .padding(8.dp),
                shape = MaterialTheme.shapes.medium) {
                Column {
                    Text(text = newsItem.title, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = newsItem.content, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Justify)

                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Przycisk do powrotu do strony głównej za pomocą navController
        Button(onClick = { navController.navigate("home") }) {
            Text(text = "STRONA GŁÓWNA")
        }

    }
}

// Komponent wyświetlający kierunki na wydziale WME
@Composable
fun Kierunki(navController: NavHostController, databaseHelper: DatabaseHelper) {

    // Pobranie listy kierunków z bazy danych
    val kierunkiList = remember {
        databaseHelper.getAllKierunki()
    }

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Kierunki studiów",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Zapraszamy do wstępnych zapisów!", color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(24.dp))

        // Kierunki są wyświetlane po kolei za pomocą pętli forEach
        kierunkiList.forEach { kierunek ->
            Surface(modifier = Modifier
                .height(100.dp)
                .width(300.dp)
                // Każdy Surface jest klikalny po naciśnięciu przenosi do widoku poszczególnego
                // kierunku za pomocą navController
                .clickable { navController.navigate("kierunek_details/${kierunek.kierunekID}") },
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primary,
                ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = kierunek.nazwa, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
                }

            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))
        // Przycisk do powrotu do strony głównej za pomocą navController
        Button(onClick = { navController.navigate("home") }) {
            Text(text = "STRONA GŁÓWNA")
        }
    }
}

// Komponent wyświetlający informacje o wybranym kierunku
@Composable
fun Kierunek_Details(navController: NavHostController, databaseHelper: DatabaseHelper, kierunekID: String) {

    // Pobranie wybranego kierunku za pomocą kierunekID z bazy danych
    val kierunek = remember {
        databaseHelper.getKierunek(kierunekID = kierunekID)
    }
    
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = kierunek!!.nazwa, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(30.dp))
        Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.primary) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = kierunek.opis, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Justify)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Ilość zapisanych na kierunek: ${kierunek.iloscZapisanych}", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        // Przycisk nawigujący do strony z zapisem na kierunek za pomocą navController
        Button(onClick = { navController.navigate("kierunek_register/$kierunekID") }) {
            Text(text = "ZAPISZ SIĘ")
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Przycisk powracający do strony ze wszystkimi kierunkami za pomocą navController
        Button(onClick = { navController.navigate("kierunki") }) {
            Text(text = "WSZYSTKIE KIERUNKI")
        }

    }

}

// Komponent służący do zapisania się na wybrany kierunek
@Composable
fun Kierunek_Register(navController: NavHostController, databaseHelper: DatabaseHelper, kierunekID: String) {

    // Pobranie kierunku z bazy danych
    val kierunek = remember {
        databaseHelper.getKierunek(kierunekID = kierunekID)
    }
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }

    val errorDialog = remember { mutableStateOf(false) }
    val successDialog = remember { mutableStateOf(false) }

    // Alert dialog wyświetla się po niepoprawnym wypełnieniu formularza
    if (errorDialog.value) {
        AlertDialog(
            onDismissRequest = {
                errorDialog.value = false },
            confirmButton = {
                Button(onClick = { errorDialog.value = false }) {
                    Text("OK")
                }
            },
            text = { Text("Proszę wypełnić wszystkie pola") }
        )
    }

    // successDialog wyświetlax się po poprawnym wypełnieniu formularza
    if (successDialog.value) {
        AlertDialog(
            onDismissRequest = { successDialog.value = false
                navController.popBackStack()
                               },
            confirmButton = {
                Button(onClick = { successDialog.value = false
                    // Funkcja popBackStack() nawiguje do poprzedniego komponentu
                    navController.popBackStack()
                }) {
                    Text("OK")
                }
            },
            text = { Text("Poprawnie zapisałeś się wstępnie na kierunek!") }
        )
    }

    Column( modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(modifier = Modifier.padding(16.dp), text = "Zapisz się na kierunek: ${kierunek!!.nazwa}", style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(48.dp))
        Text(modifier = Modifier.padding(16.dp), text = "Poniższym formularzem możesz się wstępnie zapisać na wybrany kierunek. Zapisanie się już teraz na kierunek gwarantuje pierwszeństwo wyboru kandydata do nas na uczelnię.",
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(24.dp))
        Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.primary) {
            Column(modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                // W JetPack Compose nie należy używać ViewBinding, zamiast tego wartości z
                // formularza pobuiera się bezpośrednio z TextField i zapisuje do zmiennej
                // w kompnencie
                TextField(
                    value = firstName.value,
                    onValueChange = { firstName.value = it },
                    label = { Text("Imię") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = lastName.value,
                    onValueChange = { lastName.value = it },
                    label = { Text("Nazwisko") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    // Sprawdzenie czy poprawnie wypełniony formularz i wykonanie insertStudent
                    if (firstName.value.isNotEmpty() && lastName.value.isNotEmpty()) {
                        val student = StudentModel(0, firstName.value, lastName.value, kierunekID.toInt())
                        databaseHelper.insertStudent(student)
                        successDialog.value = true
                    } else {
                        errorDialog.value = true
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.primary
                )) {
                    Text(text = "ZAPISZ SIĘ")
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "WRÓĆ")
        }
    }

}

// Komponent do preview
@Preview
@Composable
fun PreviewMainPage() {
    MainScreen(rememberNavController(), DatabaseHelper(LocalContext.current))
}
