package com.nisilab.jetpacktodo.di.viewmodel

import java.time.LocalDateTime

data class OutItem(
    val id: Int,
    var title: String,
    var deadLine: LocalDateTime,
    var tag: String,
    var text: String,
    var isFinish: Boolean,
    var isOpen: Boolean = false
)
