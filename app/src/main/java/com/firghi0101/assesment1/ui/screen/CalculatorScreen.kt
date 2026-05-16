package com.firghi0101.assesment1.ui.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.firghi0101.assesment1.R
import java.text.NumberFormat
import java.util.Locale
import com.firghi0101.assesment1.model.FuelEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(navController: NavController, viewModel: MainViewModel) {

    var jarak by remember { mutableStateOf("") }
    var konsumsi by remember { mutableStateOf("") }
    var harga by remember { mutableStateOf("") }
    var hasil by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Fuel Cost Calculator",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    TextButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Text(
                            text = "Back",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.fuel),
                contentDescription = "Fuel Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp))

            )

            TextField(
                value = jarak,
                onValueChange = {jarak = it},
                label = { Text(stringResource(R.string.distance)) },
                modifier = Modifier.fillMaxWidth(),
                isError = isError
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = konsumsi,
                onValueChange = { konsumsi = it },
                label = { Text(stringResource(R.string.consumption)) },
                modifier = Modifier.fillMaxWidth(),
                isError = isError
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = harga,
                onValueChange ={ harga = it },
                label = { Text(stringResource(R.string.price)) },
                modifier = Modifier.fillMaxWidth(),
                isError = isError
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (isError) {
                Text(
                    text =  errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Button(onClick = {

                val j = jarak.toDoubleOrNull()
                val k = konsumsi.toDoubleOrNull()
                val h = harga.toDoubleOrNull()

                if (jarak.isEmpty() || konsumsi.isEmpty() || harga.isEmpty()) {
                    isError = true
                    errorMessage = "semua input harus di isi"
                    hasil = ""
                } else if (j == null || k == null || h == null) {
                    isError = true
                    errorMessage = "input harus berupa angka"
                    hasil = ""
                } else if (k == 0.0) {
                    isError = true
                    errorMessage = "konsumsi tidak boleh 0"
                    hasil = ""
                } else {

                    isError = false
                    errorMessage = ""

                    val bensin = j / k
                    val total = bensin * h

                    val entity = FuelEntity(
                        jarak = j.toFloat(),
                        konsumsi = k.toFloat(),
                        harga = h.toFloat(),
                        totalBiaya = total
                    )

                    viewModel.insertFuel(entity)

                    val formatRupiah = NumberFormat.getCurrencyInstance(
                        Locale.forLanguageTag("id-ID")
                    )

                    hasil = "Total biaya: ${formatRupiah.format(total)}"
                }



            }) {
                Text(stringResource(R.string.start))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = hasil)

            val context = LocalContext.current

            Button(onClick = {

                val gmmIntentUri = "geo:0,0?q=SPBU terdekat".toUri()
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

                if (mapIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(mapIntent)
                }

            }) {
                Text("Cari SPBU Terdekat")
            }
        }
    }
}
