package cl.litegames

import adapter.ProductAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import data.model.Categoria
import data.model.Producto

class ShoppingList : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var createProductButton: Button
    private lateinit var productList: ListView
    private lateinit var productAdapter: ProductAdapter

    private var listaDeProductos = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)

        backButton = findViewById(R.id.button_back_shopping)
        createProductButton = findViewById(R.id.create_product)
        productList = findViewById(R.id.listView_productList)

        // Inicializar el adaptador
        productAdapter = ProductAdapter(this, R.layout.product_list, listaDeProductos)
        productList.adapter = productAdapter

        backButton.setOnClickListener {
            val aboutIntent = Intent(this, MainActivity::class.java)
            startActivity(aboutIntent)
        }
        // Si viene del MainActivity las lineas anteriores no nos necesarias. 35 a 38

        createProductButton.setOnClickListener {
            mostrarDialogoProducto()
        }
    }

    private fun mostrarDialogoProducto() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.popup_product_shopping, null)

        // Pasa la vista al builder
        builder.setView(view)

        // Obtiene referencias a los EditText y al Spinner
        val editTextNombre = view.findViewById<EditText>(R.id.editTextNombre)
        val editTextPrecio = view.findViewById<EditText>(R.id.editTextPrecio)
        val editTextCantidad = view.findViewById<EditText>(R.id.editTextCantidad)
        val editTextDescripcion = view.findViewById<EditText>(R.id.editTextDescripcion)
        val spinnerCategoria = view.findViewById<Spinner>(R.id.spinnerCategoria)

        // Obtiene el array de categorías desde resources
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
            val precio = editTextPrecio.text.toString().toIntOrNull() ?: 0
            val cantidad = editTextCantidad.text.toString().toIntOrNull() ?: 0
            val descripcion = editTextDescripcion.text.toString()
            val categoria = spinnerCategoria.selectedItem.toString()

            // Crea un objeto Producto con los datos del diálogo
            //val nuevoProducto = Producto(nombre, precio, cantidad, descripcion, Categoria.valueOf(categoria))

            // Añade el nuevo producto a la lista
            //listaDeProductos.add(nuevoProducto)

            // Notifica al adaptador
            //productAdapter.notifyDataSetChanged()

            // Utiliza los valores
            //mostrarToast("Producto añadido: $nombre")

            Log.i("ListaProductos", "Lista después de agregar un producto: $listaDeProductos")

            // Actualiza el total
            actualizarTotal()
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

    @SuppressLint("StringFormatInvalid")
    private fun actualizarTotal() {
        // Calcular la suma
        val total = listaDeProductos.sumOf { it.precio * it.cantidad }

        Log.i("total0", "El total es:$total")

        // Actualizar el texto
        val totalTextView = findViewById<TextView>(R.id.text_total_products_number)
        Log.i("total1", "El total es:"+ totalTextView.text)
        totalTextView.text = total.toString() + "\tCLP"

        Log.i("total2", "El total es:"+ totalTextView.text)
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
