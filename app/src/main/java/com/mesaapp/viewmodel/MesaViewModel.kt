package com.mesaapp.viewmodel

import androidx.lifecycle.ViewModel
import com.mesaapp.data.Item
import com.mesaapp.data.Person
import com.mesaapp.data.Table
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class MesaViewModel : ViewModel() {

    private val _table = MutableStateFlow(Table())
    val table: StateFlow<Table> = _table.asStateFlow()

    private val _currentPersonId = MutableStateFlow("")
    val currentPersonId: StateFlow<String> = _currentPersonId.asStateFlow()

    private val _currentPersonName = MutableStateFlow("")
    val currentPersonName: StateFlow<String> = _currentPersonName.asStateFlow()

    fun joinTable(tableId: String, name: String) {
        val personId = UUID.randomUUID().toString()
        _currentPersonId.value = personId
        _currentPersonName.value = name

        val newPerson = Person(id = personId, name = name, total = 0.0, paid = false)

        _table.value = _table.value.copy(
            id = tableId,
            people = _table.value.people + newPerson
        )
    }

    fun addItem(itemName: String, price: Double) {
        val people = _table.value.people
        if (people.isEmpty()) return

        val share = price / people.size
        val newItem = Item(name = itemName, price = price)

        val updatedPeople = people.map { person ->
            person.copy(total = person.total + share)
        }

        _table.value = _table.value.copy(
            items = _table.value.items + newItem,
            people = updatedPeople
        )
    }

    fun markAsPaid() {
        val personId = _currentPersonId.value
        val updatedPeople = _table.value.people.map { person ->
            if (person.id == personId) person.copy(paid = true) else person
        }
        _table.value = _table.value.copy(people = updatedPeople)
    }

    fun resetTable() {
        _table.value = Table()
        _currentPersonId.value = ""
        _currentPersonName.value = ""
    }
}
