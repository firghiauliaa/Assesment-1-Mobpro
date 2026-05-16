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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.firghi0101.assesment1.R
import com.firghi0101.assesment1.model.FuelEntity
import java.text.NumberFormat
import java.util.Locale

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
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Fuel Cost Calculator",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
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
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.fuel),
                contentDescription = "Fuel Image",
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            OutlinedTextField(
                value = jarak,
                onValueChange = { jarak = it },
                label = { Text(stringResource(R.string.distance)) },
                modifier = Modifier.fillMaxWidth(),
                isError = isError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = konsumsi,
                onValueChange = { konsumsi = it },
                label = { Text(stringResource(R.string.consumption)) },
                modifier = Modifier.fillMaxWidth(),
                isError = isError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = harga,
                onValueChange = { harga = it },
                label = { Text(stringResource(R.string.price)) },
                modifier = Modifier.fillMaxWidth(),
                isError = isError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            if (isError) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }

            Button(
                onClick = {
                    val j = jarak.toDoubleOrNull()
                    val k = konsumsi.toDoubleOrNull()
                    val h = harga.toDoubleOrNull()

                    if (jarak.isEmpty() || konsumsi.isEmpty() || harga.isEmpty()) {
                        isError = true
                        errorMessage = "Semua input harus diisi"
                        hasil = ""
                    } else if (j == null || k == null || h == null) {
                        isError = true
                        errorMessage = "Input harus berupa angka"
                        hasil = ""
                    } else if (k == 0.0) {
                        isError = true
                        errorMessage = "Konsumsi tidak boleh 0"
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

                        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                        hasil = "Total Biaya: ${formatRupiah.format(total)}"
                    }
                },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text(stringResource(R.string.start), fontWeight = FontWeight.Bold)
            }

            if (hasil.isNotEmpty()) {
                Text(
                    text = hasil,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            val context = LocalContext.current

            Button(
                onClick = {
                    val gmmIntentUri = "geo:0,0?q=SPBU terdekat".toUri()
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    context.startActivity(mapIntent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Cari SPBU Terdekat", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}