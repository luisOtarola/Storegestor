package data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actions")
data class Action(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String?,
    val accion: String?,
)




