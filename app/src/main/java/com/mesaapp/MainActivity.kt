package com.mesaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.mesaapp.ui.screens.JoinScreen
import com.mesaapp.ui.screens.MesaScreen
import com.mesaapp.ui.theme.MesaAppTheme
import com.mesaapp.viewmodel.MesaViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MesaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MesaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var joined by remember { mutableStateOf(false) }

                    if (joined) {
                        MesaScreen(
                            viewModel = viewModel,
                            onReset = { joined = false }
                        )
                    } else {
                        JoinScreen(
                            viewModel = viewModel,
                            onJoined = { joined = true }
                        )
                    }
                }
            }
        }
    }
}
