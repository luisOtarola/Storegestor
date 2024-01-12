package model

import android.os.Parcel
import android.os.Parcelable

class Producto(

    val nombre: String?,
    val precio: Double,
    val cantidad: Int,
    val descripcion: String?,
    val categoria: Categoria,
): Parcelable

{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(Categoria::class.java.classLoader)!!
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeDouble(precio)
        parcel.writeInt(cantidad)
        parcel.writeString(descripcion)
        parcel.writeParcelable(categoria, flags)
    }

    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<Producto> {
        override fun createFromParcel(parcel: Parcel): Producto {
            return Producto(parcel)
        }

        override fun newArray(size: Int): Array<Producto?> {
            return arrayOfNulls(size)
        }
    }

    fun obtenerDetalles(): String {
        return "Nombre: $nombre, Precio: $precio, Cantidad: $cantidad, Descripción: $descripcion, Categoría: $categoria"
    }
}
