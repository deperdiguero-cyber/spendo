package com.mesaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mesaapp.viewmodel.MesaViewModel

@Composable
fun JoinScreen(viewModel: MesaViewModel, onJoined: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var tableId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🍽️", fontSize = 64.sp, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "MesaApp",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Dividí la cuenta sin drama",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Tu nombre") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tableId,
            onValueChange = { tableId = it.uppercase() },
            label = { Text("ID de la mesa (ej: MESA1)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && tableId.isNotBlank()) {
                    viewModel.joinTable(tableId.trim(), name.trim())
                    onJoined()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = name.isNotBlank() && tableId.isNotBlank()
        ) {
            Text("Entrar a la mesa", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
