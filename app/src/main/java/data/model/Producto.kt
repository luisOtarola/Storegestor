package data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String?,
    val precio: Int,
    val cantidad: Int,
    val descripcion: String?,
    val categoria: Categoria
)  {
    fun obtenerDetalles(): String {
        return "Nombre: $nombre, Precio: $precio, Cantidad: $cantidad, Descripción: $descripcion, Categoría: $categoria"
    }
}

