package com.nisilab.jetpacktodo.di.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nisilab.jetpacktodo.di.Model.OutItem
import java.io.Serializable
import java.time.LocalDateTime

@Entity(tableName = "todo_table")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var deadLine: LocalDateTime,
    var tag: String?,
    var text: String?,
    var isFinish: Boolean = false

):Serializable {
    fun changeFinishFlg(): TodoItem{
        return this.copy(isFinish = !this.isFinish)
    }

    fun toOutItem(): OutItem {
        return OutItem(this)
    }
}