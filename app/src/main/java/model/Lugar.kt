
import android.os.Parcel
import android.os.Parcelable

class Lugar(
    val nombre: String?,
    val direccion: String?,
    val descripcion: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(direccion)
        parcel.writeString(descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lugar> {
        override fun createFromParcel(parcel: Parcel): Lugar {
            return Lugar(parcel)
        }

        override fun newArray(size: Int): Array<Lugar?> {
            return arrayOfNulls(size)
        }
    }

    fun obtenerDetalles(): String {
        return "Nombre: $nombre, Dirección: $direccion, Descripción: $descripcion"
    }
}
