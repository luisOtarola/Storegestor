package adapter

import Lugar
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cl.litegames.R

class PlaceAdapter(
    context : Context,
    resource : Int,
    places : List<Lugar>): ArrayAdapter<Lugar>(context, resource, places) {

    override fun getView(pos : Int, convertView: View?, parent : ViewGroup) : View {

        var listItemView = convertView
        val holder: PlaceAdapter.ViewHolder

        if (listItemView == null)
        {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            listItemView = inflater.inflate(R.layout.place_list, parent, false)

            holder = PlaceAdapter.ViewHolder()
            holder.nombreTextView = listItemView.findViewById(R.id.textView_listView_nombre_place)
            holder.direccionTextView = listItemView.findViewById(R.id.textView_listView_direction_place)
            holder.descripcionTextView = listItemView.findViewById(R.id.textView_listView_description_place)

            listItemView.tag = holder
        }
        else
        {
            holder = listItemView.tag as PlaceAdapter.ViewHolder
        }

        val lugar = getItem(pos)

        holder.nombreTextView.text = context.getString(R.string.wordNamePlace) + ": " + lugar?.nombre
        holder.direccionTextView.text = context.getString(R.string.wordDirectionPlace) + ": " + lugar?.direccion
        holder.descripcionTextView.text = context.getString(R.string.wordDescriptionPlace) + ": " + lugar?.descripcion

        return listItemView!!
    }
    private class ViewHolder
    {
        lateinit var nombreTextView: TextView
        lateinit var direccionTextView: TextView
        lateinit var descripcionTextView: TextView
    }
}