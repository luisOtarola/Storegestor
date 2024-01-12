package cl.litegames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import model.Categoria
import model.Producto

class ShoppingList : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var createProductButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)

        backButton = findViewById(R.id.button_back_shopping) as ImageView
        createProductButton = findViewById(R.id.create_product) as Button

        backButton.setOnClickListener {
            val aboutIntent = Intent(this, MainActivity::class.java)
            startActivity(aboutIntent)
        }
        createProductButton.setOnClickListener {
            mostrarDialogoProducto()
        }


    }
    /*private fun mostrarDialogo() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Nuevo Producto")
        builder.setMessage("¿Deseas agregar un nuevo producto?")

        // Configurar un botón de "Aceptar"
        builder.setPositiveButton("Aceptar") { _, _ ->
            mostrarDialogoProducto()
        }

        // Configurar un botón de "Cancelar"
        builder.setNegativeButton("Cancelar") { dialog, _ ->

            dialog.dismiss() // Cierra el cuadro de diálogo
        }

        // Mostrar el cuadro de diálogo
        val dialog = builder.create()
        dialog.show()
    }*/

    private fun mostrarDialogoProducto() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.popup_product_shopping, null)

        // Pasa la vista al builder
        builder.setView(view)

        // Obtén referencias a los EditText y al Spinner
        val editTextNombre = view.findViewById<EditText>(R.id.editTextNombre)
        val editTextPrecio = view.findViewById<EditText>(R.id.editTextPrecio)
        val editTextCantidad = view.findViewById<EditText>(R.id.editTextCantidad)
        val editTextDescripcion = view.findViewById<EditText>(R.id.editTextDescripcion)
        val spinnerCategoria = view.findViewById<Spinner>(R.id.spinnerCategoria)

        // Obtén el array de categorías desde resources
        val categoriasArray = resources.getStringArray(R.array.categorias)

        // Crea un ArrayAdapter utilizando el array y el diseño por defecto del Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoriasArray)

        // Especifica el diseño a utilizar cuando se despliega el Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asigna el adaptador al Spinner
        spinnerCategoria.adapter = adapter

        // Agrega botones "Aceptar" y "Cancelar"
        builder.setPositiveButton("Aceptar") { _, _ ->
            val nombre = editTextNombre.text.toString()
            val precio = editTextPrecio.text.toString().toDoubleOrNull() ?: 0.0
            val cantidad = editTextCantidad.text.toString().toIntOrNull() ?: 0
            val descripcion = editTextDescripcion.text.toString()
            val categoria = spinnerCategoria.selectedItem.toString()

            // Crea un objeto Producto con los datos del diálogo
            val nuevoProducto = Producto(nombre, precio, cantidad, descripcion, Categoria.valueOf(categoria))

            // Utiliza los valores como desees (puedes mostrarlos, guardarlos, etc.)
            mostrarToast("Nombre: $nombre, Precio: $precio, Cantidad: $cantidad, Descripción: $descripcion, Categoría: $categoria")

            // Guarda el nuevo producto
            //listaDeProductos.add(nuevoProducto)

        }

        // Configurar un botón de "Cancelar"
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss() // Cierra el cuadro de diálogo
        }

        // Muestra el cuadro de diálogo
        val dialog = builder.create()

        // Configurar la atenuación del fondo detrás del diálogo
        dialog.window?.setDimAmount(0.5f) // Ajusta este valor según sea necesario (0.0f para nada de atenuación, 1.0f para atenuación completa)

        dialog.show()
    }


    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

}