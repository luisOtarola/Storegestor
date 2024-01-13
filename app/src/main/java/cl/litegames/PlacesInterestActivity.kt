package cl.litegames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import Lugar
import adapter.PlaceAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class PlacesInterestActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var createPlaceButton: Button
    private lateinit var placeList: ListView
    private lateinit var placeAdapter: PlaceAdapter

    private var listaDeLugares= mutableListOf<Lugar>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places_interest)

        backButton = findViewById(R.id.button_back_places) as ImageView
        createPlaceButton = findViewById(R.id.create_buttonAdd_place)
        placeList = findViewById(R.id.listView_placeList_place)

        // Inicializar el adaptador
        placeAdapter = PlaceAdapter(this, R.layout.product_list, listaDeLugares)
        placeList.adapter = placeAdapter

        backButton.setOnClickListener {
            val aboutIntent = Intent(this, MainActivity::class.java)
            startActivity(aboutIntent)
        }
        createPlaceButton.setOnClickListener {
            mostrarDialogoLugar()
        }
    }
    private fun mostrarDialogoLugar() {

        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.popup_place, null)

        // Pasa la vista al builder
        builder.setView(view)

        // Obtiene referencias a los EditText y al Spinner
        val editTextNombre = view.findViewById<EditText>(R.id.editTextNombre_place)
        val editTextDireccion = view.findViewById<EditText>(R.id.editTextDescripcion_place)
        val editTextDescripcion = view.findViewById<EditText>(R.id.editTextDescripcion_place)

        // Agrega botones "Aceptar" y "Cancelar"
        builder.setPositiveButton("Aceptar") { _, _ ->
            val nombre = editTextNombre.text.toString()
            val direccion = editTextDireccion.text.toString()
            val descripcion = editTextDescripcion.text.toString()

            // Crea un objeto Producto con los datos del diálogo
            val nuevoLugar = Lugar(nombre, direccion, descripcion )

            // Añade el nuevo producto a la lista
            listaDeLugares.add(nuevoLugar)

            // Notifica al adaptador
            placeAdapter.notifyDataSetChanged()

            // Utiliza los valores
            mostrarToast("Lugar añadido: $nombre")
        }
        // Configurar un botón de "Cancelar"
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss() // Cierra el cuadro de diálogo
        }

        // Muestra el cuadro de diálogo
        val dialog = builder.create()

        // Configurar la atenuación del fondo detrás del diálogo
        dialog.window?.setDimAmount(0.5f)

        dialog.show()
    }
    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}