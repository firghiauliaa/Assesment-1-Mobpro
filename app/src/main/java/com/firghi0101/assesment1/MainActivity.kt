package com.firghi0101.assesment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.firghi0101.assesment1.navigation.NavGraph
import com.firghi0101.assesment1.ui.theme.Assesment1Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Assesment1Theme {
                NavGraph()
            }
        }
    }
}