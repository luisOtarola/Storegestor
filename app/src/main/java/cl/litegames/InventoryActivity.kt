package cl.litegames

import adapter.ProductAdapterMin
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import data.ViewModel.ProductViewModel
import data.model.Categoria
import data.model.Producto

class InventoryActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var createProductButton: Button
    private lateinit var productList: ListView
    private lateinit var productAdapterMin: ProductAdapterMin
    private lateinit var editTextSearch: EditText
    private lateinit var mProductViewModel: ProductViewModel

    private var listaDeProductos = mutableListOf<Producto>()

    private var sortOrder = SortOrder.NONE

    enum class SortOrder {
        NONE,
        NAME_ASC,
        NAME_DESC,
        PRICE_ASC,
        PRICE_DESC,
        QUANTITY_ASC,
        QUANTITY_DESC,
        CATEGORY_ASC,
        CATEGORY_DESC
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)

        editTextSearch = findViewById(R.id.editTextSearch)
        backButton = findViewById(R.id.button_back_inventory)
        createProductButton = findViewById(R.id.create_product_inventory)
        productList = findViewById(R.id.listView_productList_Inventory)

        val toolbar: Toolbar = findViewById(R.id.toolbar_inventory)
        setSupportActionBar(toolbar)
        supportActionBar?.title = null

        productAdapterMin = ProductAdapterMin(
            this,
            R.layout.custom_row,
            listaDeProductos,
            { position -> editarProducto(position) },
            { position -> eliminarProducto(position) },
            { position -> verDetallesProducto(position) }
        )
        productList.adapter = productAdapterMin

        backButton.setOnClickListener {
            val aboutIntent = Intent(this, MainActivity::class.java)
            startActivity(aboutIntent)
        }
        createProductButton.setOnClickListener {
            mostrarDialogoProducto()
        }

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        mProductViewModel.getAllData.observe(this) { productos ->
            listaDeProductos.clear()
            listaDeProductos.addAll(productos)
            applySorting()
            productAdapterMin.notifyDataSetChanged()
        }

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buscarPorNombre(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val refreshIcon = findViewById<ImageView>(R.id.refreshIcon)
        refreshIcon.setOnClickListener {
            mProductViewModel.getAllData.observe(this) { productos ->
                listaDeProductos.clear()
                listaDeProductos.addAll(productos)
                applySorting()
                productAdapterMin.notifyDataSetChanged()
            }
        }
    }


    private fun buscarPorNombre(nombre: String) {
        val filteredList = listaDeProductos.filter { it.nombre?.contains(nombre, true) == true }
        applySorting()
        productAdapterMin.actualizarLista(filteredList)
        productAdapterMin.notifyDataSetChanged()
    }
    private fun buscarPorCategoria(categoria: String) {
        val filteredList = listaDeProductos.filter { it.categoria.toString().equals(categoria, ignoreCase = true) }
        applySorting()
        productAdapterMin.actualizarLista(filteredList)
        productAdapterMin.notifyDataSetChanged()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_inventory, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.inventory_action_search -> {
                //val Act = Intent(this, SearchProductActivity::class.java)
                //startActivity(Act)
                showCategorySearchDialog()
                return true
            }
            R.id.inventory_action_orderBy -> {
                showOrderByDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showOrderByDialog() {
        val orderOptions = arrayOf(
            "Nombre Asc", "Nombre Desc",
            "Precio Asc", "Precio Desc",
            "Cantidad Asc", "Cantidad Desc",
        )

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ordenar por")
            .setItems(orderOptions) { _, which ->
                when (which) {
                    0 -> sortOrder = SortOrder.NAME_ASC
                    1 -> sortOrder = SortOrder.NAME_DESC
                    2 -> sortOrder = SortOrder.PRICE_ASC
                    3 -> sortOrder = SortOrder.PRICE_DESC
                    4 -> sortOrder = SortOrder.QUANTITY_ASC
                    5 -> sortOrder = SortOrder.QUANTITY_DESC
                }

                if (which in 6..7) {
                    showCategorySearchDialog()
                } else {
                    applySorting()
                }
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showCategorySearchDialog() {
        val categoriasArray = resources.getStringArray(R.array.categorias)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccionar categoría")
            .setItems(categoriasArray) { _, which ->
                val selectedCategory = categoriasArray[which]
                buscarPorCategoria(selectedCategory)
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun applySorting() {
        when (sortOrder) {
            SortOrder.NAME_ASC -> orderByNameAsc()
            SortOrder.NAME_DESC -> orderByNameDesc()
            SortOrder.PRICE_ASC -> orderByPriceAsc()
            SortOrder.PRICE_DESC -> orderByPriceDesc()
            SortOrder.QUANTITY_ASC -> orderByQuantityAsc()
            SortOrder.QUANTITY_DESC -> orderByQuantityDesc()
            SortOrder.CATEGORY_ASC -> orderByCategoryAsc()
            SortOrder.CATEGORY_DESC -> orderByCategoryDesc()
            else -> {
                // Handle NONE or any other cases if needed
            }
        }
    }

    private fun orderByNameAsc() {
        listaDeProductos.sortBy { it.nombre }
        productAdapterMin.notifyDataSetChanged()
    }

    private fun orderByNameDesc() {
        listaDeProductos.sortByDescending { it.nombre }
        productAdapterMin.notifyDataSetChanged()
    }

    private fun orderByPriceAsc() {
        listaDeProductos.sortBy { it.precio }
        productAdapterMin.notifyDataSetChanged()
    }

    private fun orderByPriceDesc() {
        listaDeProductos.sortByDescending { it.precio }
        productAdapterMin.notifyDataSetChanged()
    }

    private fun orderByQuantityAsc() {
        listaDeProductos.sortBy { it.cantidad }
        productAdapterMin.notifyDataSetChanged()
    }

    private fun orderByQuantityDesc() {
        listaDeProductos.sortByDescending { it.cantidad }
        productAdapterMin.notifyDataSetChanged()
    }

    private fun orderByCategoryAsc() {
        listaDeProductos.sortBy { it.categoria.toString() }
        productAdapterMin.notifyDataSetChanged()
    }

    private fun orderByCategoryDesc() {
        listaDeProductos.sortByDescending { it.categoria.toString() }
        productAdapterMin.notifyDataSetChanged()
    }

    private fun verDetallesProducto(position: Int) {
        val producto = listaDeProductos[position]
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra("PRODUCT_ID", producto.id)
        startActivity(intent)
    }

    private fun eliminarProducto(position: Int) {
        val producto = listaDeProductos[position]
        mProductViewModel.deleteProduct(producto)
    }

    private fun editarProducto(position: Int) {
        val producto = listaDeProductos[position]
        val intent = Intent(this, EditProductActivity::class.java)
        intent.putExtra("PRODUCT_ID", producto.id)
        startActivity(intent)
    }

    private fun mostrarDialogoProducto() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.popup_product_inventory, null)
        builder.setView(view)

        val editTextNombre = view.findViewById<EditText>(R.id.editTextNombre_inventory)
        val editTextPrecio = view.findViewById<EditText>(R.id.editTextPrecio_inventory)
        val editTextCantidad = view.findViewById<EditText>(R.id.editTextCantidad_inventory)
        val editTextDescripcion = view.findViewById<EditText>(R.id.editTextDescripcion_inventory)
        val spinnerCategoria = view.findViewById<Spinner>(R.id.spinnerCategoria_inventory)

        val categoriasArray = resources.getStringArray(R.array.categorias)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoriasArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = adapter

        builder.setPositiveButton("Aceptar") { _, _ ->
            val nombre = editTextNombre.text.toString()
            val precio = editTextPrecio.text.toString().toIntOrNull() ?: 0
            val cantidad = editTextCantidad.text.toString().toIntOrNull() ?: 0
            val descripcion = editTextDescripcion.text.toString()
            val categoria = spinnerCategoria.selectedItem.toString()

            if (inputCheck(nombre, precio, cantidad, descripcion)) {
                val producto = Producto(0, nombre, precio, cantidad, descripcion, Categoria.valueOf(categoria))
                mProductViewModel.addProduct(producto)
                Toast.makeText(this, "Successfully added!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Please fill out all fields!", Toast.LENGTH_LONG).show()
            }

            productAdapterMin.notifyDataSetChanged()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
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
}
