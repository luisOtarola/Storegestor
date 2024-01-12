package model

class Producto(

    val nombre: String?,
    val precio: Double,
    val cantidad: Int,
    val descripcion: String?,
    val categoria: Categoria,
){
    fun obtenerDetalles(): String {
        return "Nombre: $nombre, Precio: $precio, Cantidad: $cantidad, Descripción: $descripcion, Categoría: $categoria"
    }
}
