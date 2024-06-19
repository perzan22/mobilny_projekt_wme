package com.example.projekt_amw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    private lateinit var databseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        databseHelper = DatabaseHelper(this)
        setContent {
            Projekt_amwTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MainScreen(navController, databseHelper)
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController, databaseHelper: DatabaseHelper) {
    NavHost(navController, startDestination = "home") {
        composable("home") { MainPage(navController) }
        composable("aktualnosci") { Aktualnosci(navController, databaseHelper) }
        composable("kierunki") { Kierunki(navController, databaseHelper) }
    }
}

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
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { navController.navigate("aktualnosci") }) {
            Text(text = "Zobacz aktualności")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Kierunki studiów",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { navController.navigate("kierunki") }) {
            Text(text = "Zobacz kierunki")
        }

    }

}

@Composable
fun Aktualnosci(navController: NavHostController, databaseHelper: DatabaseHelper) {
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
        Spacer(modifier = Modifier.height(24.dp))

        newsList.forEach { newsItem ->
            Text(text = newsItem.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = newsItem.content, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(20.dp))
        }

        Button(onClick = { navController.navigate("home") }) {
            Text(text = "STRONA GŁÓWNA")
        }

    }
}

@Composable
fun Kierunki(navController: NavHostController, databaseHelper: DatabaseHelper) {
    val kierunkiList = remember {
        databaseHelper.getAllKierunki()
    }

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start) {
        Text(text = "Kierunki studiów",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(24.dp))

        kierunkiList.forEach { kierunek ->
            Text(text = kierunek.nazwa, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(24.dp))
        }

        Button(onClick = { navController.navigate("home") }) {
            Text(text = "STRONA GŁÓWNA")
        }
    }
}

@Preview
@Composable
fun PreviewMainPage() {
    MainScreen(rememberNavController(), DatabaseHelper(LocalContext.current))
}