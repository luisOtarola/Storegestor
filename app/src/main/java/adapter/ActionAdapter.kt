package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cl.litegames.R
import data.model.Action

class ActionAdapter(
    context: Context,
    resource: Int,
    products: List<Action>,
) : ArrayAdapter<Action>(context, resource, products) {

    fun actualizarLista(nuevaLista: List<Action>) {
        clear()
        addAll(nuevaLista)
        notifyDataSetChanged()
    }

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {

        var listItemView = convertView
        val holder: ViewHolder

        if (listItemView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            listItemView = inflater.inflate(R.layout.action_list, parent, false)

            holder = ViewHolder()
            holder.nombreTextView = listItemView.findViewById(R.id.text_listView_nombreProducto)
            holder.actionTextView = listItemView.findViewById(R.id.text_listView_action)

            listItemView.tag = holder

        } else {
            holder = listItemView.tag as ViewHolder
        }

        val action = getItem(pos)

        holder.nombreTextView.text = action?.nombre
        holder.actionTextView.text = action?.accion

        return listItemView!!
    }

    private class ViewHolder {
        lateinit var nombreTextView: TextView
        lateinit var actionTextView: TextView
    }
}
