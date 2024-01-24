package adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import cl.litegames.R
import data.model.Producto
class ProductAdapterMin (

    context: Context,
    resource: Int,
    products: List<Producto>,
    private val editClickListener: (Int) -> Unit,
    private val deleteClickListener: (Int) -> Unit,
    private val detailClickListener: (Int) -> Unit
) : ArrayAdapter<Producto>(context, resource, products) {

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {

        var listItemView = convertView
        val holder: ViewHolder

        if (listItemView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            listItemView = inflater.inflate(R.layout.custom_row, parent, false)

            holder = ViewHolder()
            holder.nombreTextView = listItemView.findViewById(R.id.text_nombre_customRow)
            holder.precioTextView = listItemView.findViewById(R.id.text_precio_customRow)
            holder.cantidadTextView = listItemView.findViewById(R.id.text_cantidad_customRow)
            holder.editIcon = listItemView.findViewById(R.id.editIcon2)
            holder.deleteIcon = listItemView.findViewById(R.id.deleteIcon2)
            holder.seeDetailIcon = listItemView.findViewById(R.id.seeDetailIcon2)

            listItemView.tag = holder

        } else {
            holder = listItemView.tag as ViewHolder
        }

        val producto = getItem(pos)

        holder.nombreTextView.text = producto?.nombre
        holder.precioTextView.text =  producto?.precio.toString() + " CLP"
        holder.cantidadTextView.text =producto?.cantidad.toString()

        holder.editIcon.setOnClickListener {
            // Manejar clic en el icono de editar
            editClickListener.invoke(pos)
        }

        holder.deleteIcon.setOnClickListener {
            // Manejar clic en el icono de eliminar
            deleteClickListener.invoke(pos)
        }

        holder.seeDetailIcon.setOnClickListener {
            // Manejar clic en el icono de ver detalles
            detailClickListener.invoke(pos)
        }
        return listItemView!!
    }

    private class ViewHolder {
        lateinit var nombreTextView: TextView
        lateinit var precioTextView: TextView
        lateinit var cantidadTextView: TextView
        lateinit var editIcon: ImageView
        lateinit var deleteIcon: ImageView
        lateinit var seeDetailIcon: ImageView
    }
}
