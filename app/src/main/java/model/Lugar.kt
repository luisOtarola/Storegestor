package model

class Lugar (
    val nombre: String?,
    val direccion: String?,
    val descripcion: String?,
){
    fun obtenerDetalles(): String {
        return "Nombre: $nombre, Dirección: $direccion Descripción: $descripcion"
    }
}