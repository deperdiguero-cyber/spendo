package com.mesaapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mesaapp.data.Person
import com.mesaapp.viewmodel.MesaViewModel

@Composable
fun MesaScreen(viewModel: MesaViewModel, onReset: () -> Unit) {
    val table by viewModel.table.collectAsState()
    val currentPersonId by viewModel.currentPersonId.collectAsState()

    var itemName by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("") }
    var showResetDialog by remember { mutableStateOf(false) }

    val currentPerson = table.people.find { it.id == currentPersonId }
    val myTotal = currentPerson?.total ?: 0.0
    val alreadyPaid = currentPerson?.paid ?: false

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Nueva mesa") },
            text = { Text("¿Querés limpiar todo y empezar de nuevo?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.resetTable()
                    showResetDialog = false
                    onReset()
                }) { Text("Sí, empezar de nuevo") }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) { Text("Cancelar") }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mesa: ${table.id}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    TextButton(onClick = { showResetDialog = true }) {
                        Text("Nueva mesa", style = MaterialTheme.typography.bodySmall)
                    }
                }
                Text(
                    text = "${table.people.size} persona(s) · ${table.items.size} consumo(s)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }

        HorizontalDivider()

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // People section
            item {
                Text(
                    text = "PERSONAS",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            items(table.people, key = { it.id }) { person ->
                PersonCard(
                    person = person,
                    isCurrentUser = person.id == currentPersonId
                )
            }

            // Items section (if any)
            if (table.items.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "CONSUMOS",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                items(table.items, key = { it.id }) { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(item.name, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "$${"%.2f".format(item.price)}",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Add item form
            item {
                Text(
                    text = "AGREGAR CONSUMO",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = itemName,
                            onValueChange = { itemName = it },
                            label = { Text("Producto") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = itemPrice,
                            onValueChange = { itemPrice = it },
                            label = { Text("Precio") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            prefix = { Text("$") }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                val price = itemPrice.toDoubleOrNull()
                                if (itemName.isNotBlank() && price != null && price > 0) {
                                    viewModel.addItem(itemName.trim(), price)
                                    itemName = ""
                                    itemPrice = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = itemName.isNotBlank() && (itemPrice.toDoubleOrNull() ?: 0.0) > 0
                        ) {
                            Text("Agregar")
                        }
                    }
                }
            }
        }

        // Bottom bar: my total + pay button
        HorizontalDivider()
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Tu total",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "$${"%.2f".format(myTotal)}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Button(
                    onClick = { viewModel.markAsPaid() },
                    enabled = !alreadyPaid && myTotal > 0,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (alreadyPaid)
                            MaterialTheme.colorScheme.secondary
                        else
                            MaterialTheme.colorScheme.primary,
                        disabledContainerColor = if (alreadyPaid)
                            MaterialTheme.colorScheme.secondary
                        else
                            MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text(
                        text = if (alreadyPaid) "✓ Pagado" else "Pagar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun PersonCard(person: Person, isCurrentUser: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrentUser)
                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (person.paid) "✅" else "⏳",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = person.name + if (isCurrentUser) " (vos)" else "",
                        fontWeight = if (isCurrentUser) FontWeight.Bold else FontWeight.Normal,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = if (person.paid) "Pagado" else "Pendiente",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (person.paid)
                            MaterialTheme.colorScheme.secondary
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }
            Text(
                text = "$${"%.2f".format(person.total)}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                color = if (person.paid)
                    MaterialTheme.colorScheme.secondary
                else
                    MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
