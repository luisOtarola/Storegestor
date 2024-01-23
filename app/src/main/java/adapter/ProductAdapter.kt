package adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cl.litegames.R
import data.model.Producto

class ProductAdapter(
    context : Context,
    resource : Int,
    products : List<Producto>): ArrayAdapter<Producto>(context, resource, products){

    override fun getView(pos : Int, convertView: View?, parent : ViewGroup) : View{

        var listItemView = convertView
        val holder: ViewHolder

        if (listItemView == null)
        {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            listItemView = inflater.inflate(R.layout.product_list, parent, false)

            holder = ViewHolder()
            holder.nombreTextView = listItemView.findViewById(R.id.textView_listView_nombre)
            holder.precioTextView = listItemView.findViewById(R.id.textView_listView_precio)
            holder.cantidadTextView = listItemView.findViewById(R.id.textView_listView_cantidad)
            holder.descripcionTextView = listItemView.findViewById(R.id.textView_listView_descripcion)
            holder.categoriaTextView = listItemView.findViewById(R.id.textView_listView_categoria)

            listItemView.tag = holder
        }
        else
        {
            holder = listItemView.tag as ViewHolder
        }

        val producto = getItem(pos)

        holder.nombreTextView.text = context.getString(R.string.wordName) + ": " + producto?.nombre
        holder.precioTextView.text = context.getString(R.string.wordPrice) + ": " + producto?.precio
        holder.cantidadTextView.text = context.getString(R.string.wordAmount) + ": " + producto?.cantidad
        holder.descripcionTextView.text = context.getString(R.string.wordDescription) + ": " + producto?.descripcion
        holder.categoriaTextView.text = context.getString(R.string.wordCategory) + ": " + producto?.categoria

        return listItemView!!
    }

    private class ViewHolder
    {
        lateinit var nombreTextView: TextView
        lateinit var precioTextView: TextView
        lateinit var cantidadTextView: TextView
        lateinit var descripcionTextView: TextView
        lateinit var categoriaTextView: TextView
    }
}
