package data.model

import android.os.Parcel
import android.os.Parcelable

enum class Categoria : Parcelable {
    FRUTAS,
    VERDURAS,
    CARNES,
    LACTEOS,
    PANADERIA,
    CEREALES_Y_GRANOS,
    BEBIDAS,
    CONGELADOS,
    SNACKS;

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Categoria> {
        override fun createFromParcel(parcel: Parcel): Categoria {
            return valueOf(parcel.readString()!!)
        }

        override fun newArray(size: Int): Array<Categoria?> {
            return arrayOfNulls(size)
        }
    }
}


