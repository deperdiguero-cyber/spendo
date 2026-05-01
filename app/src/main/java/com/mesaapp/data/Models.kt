package com.mesaapp.data

import java.util.UUID

data class Person(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val total: Double = 0.0,
    val paid: Boolean = false
)

data class Item(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val price: Double = 0.0
)

data class Table(
    val id: String = "",
    val people: List<Person> = emptyList(),
    val items: List<Item> = emptyList()
)
