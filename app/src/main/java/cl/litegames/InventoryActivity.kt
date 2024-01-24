package cl.litegames

import data.Dao.ProductoDao
import adapter.ProductAdapter
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import data.ViewModel.ProductViewModel
import data.model.Categoria
import data.model.Producto

class InventoryActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var createProductButton: Button
    private lateinit var productList: ListView
    private lateinit var productAdapter: ProductAdapter

    // Var interfaz y DB
    private lateinit var mProductViewModel: ProductViewModel

    private var listaDeProductos = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)

        backButton = findViewById(R.id.button_back_inventory)
        createProductButton = findViewById(R.id.create_product_inventory)
        productList = findViewById(R.id.listView_productList_Inventory)

        // Inicializar el adaptador
        productAdapter = ProductAdapter(this, R.layout.product_list, listaDeProductos)
        productList.adapter = productAdapter

        backButton.setOnClickListener {
            val aboutIntent = Intent(this, MainActivity::class.java)
            startActivity(aboutIntent)
        }
        createProductButton.setOnClickListener {
            mostrarDialogoProducto()
        }

        // Mi viewmModel
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
    }

    private fun mostrarDialogoProducto() {

        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.popup_product_inventory, null)

        // Pasa la vista al builder
        builder.setView(view)

        // Obtiene referencias a los EditText y al Spinner
        val editTextNombre = view.findViewById<EditText>(R.id.editTextNombre_inventory)
        val editTextPrecio = view.findViewById<EditText>(R.id.editTextPrecio_inventory)
        val editTextCantidad = view.findViewById<EditText>(R.id.editTextCantidad_inventory)
        val editTextDescripcion = view.findViewById<EditText>(R.id.editTextDescripcion_inventory)
        val spinnerCategoria = view.findViewById<Spinner>(R.id.spinnerCategoria_inventory)

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

            if(inputCheck(nombre,precio,cantidad,descripcion)){
                val producto = Producto(0,nombre,precio,cantidad,descripcion, Categoria.valueOf(categoria))
                mProductViewModel.addProduct(producto)
                Toast.makeText(this,"Successfully added!", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"Please fill out all fields!", Toast.LENGTH_LONG).show()
            }

            // Notifica al adaptador
            productAdapter.notifyDataSetChanged()

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

    private fun inputCheck(
        nombre: String,
        precio: Int,
        cantidad: Int,
        descripcion: String
    ): Boolean {
        return !(TextUtils.isEmpty(nombre) || precio <= 0 || cantidad <= 0 || TextUtils.isEmpty(descripcion))
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
