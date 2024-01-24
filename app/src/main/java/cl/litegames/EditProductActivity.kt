package cl.litegames

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import data.ViewModel.ProductViewModel
import data.model.Categoria
import data.model.Producto


class EditProductActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var productNameEditText: EditText
    private lateinit var productPriceEditText: EditText
    private lateinit var productQuantityEditText: EditText
    private lateinit var productDescriptionEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var mProductViewModel: ProductViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)

        backButton = findViewById(R.id.button_back_editProduct)
        productNameEditText = findViewById(R.id.editTextProductName)
        productPriceEditText = findViewById(R.id.editTextProductPrice)
        productQuantityEditText = findViewById(R.id.editTextProductQuantity)
        productDescriptionEditText = findViewById(R.id.editTextProductDescription)
        saveButton = findViewById(R.id.buttonSaveEdit)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        val productId = intent.getLongExtra("PRODUCT_ID", -1)

        Log.i("idEdit", "id del producto: $productId")
        Log.i("idEdit", "id del producto: $productId")
        if (productId.toInt() != -1) {
            mProductViewModel.getProductById(productId.toInt()).observe(this) { product ->
                product?.let { fillFieldsWithProductData(it) }
            }
        }

        saveButton.setOnClickListener {
            guardarEdicion(productId.toLong())
        }

        backButton.setOnClickListener {
            val aboutIntent = Intent(this, InventoryActivity::class.java)
            startActivity(aboutIntent)
        }
    }

    private fun fillFieldsWithProductData(product: Producto) {
        productNameEditText.setText(product.nombre)
        productPriceEditText.setText(product.precio.toString())
        productQuantityEditText.setText(product.cantidad.toString())
        productDescriptionEditText.setText(product.descripcion)

        // Deshabilitar la edición para nombre y descripción
        productNameEditText.isEnabled = false
        productDescriptionEditText.isEnabled = false
    }

    private fun guardarEdicion(productId: Long) {
        Log.i("idEdit", "id del producto abajo: $productId")
        // El nombre y la descripción no se obtienen, ya que no se modifican

        val precio = productPriceEditText.text.toString().toIntOrNull() ?: 0
        val cantidad = productQuantityEditText.text.toString().toIntOrNull() ?: 0

        if (inputCheck(precio, cantidad)) {
            // Observar el LiveData para obtener el producto actual antes de la actualización
            mProductViewModel.getProductById(productId.toInt()).observe(this) { currentProduct ->
                currentProduct?.let {
                    // Obtener la categoría actual del producto antes de la actualización
                    val currentCategoria = it.categoria ?: Categoria.DEFAULT

                    // Utilizamos Categoria.DEFAULT directamente como enum
                    val editedProduct = Producto(productId, it.nombre, precio, cantidad, it.descripcion, currentCategoria)
                    mProductViewModel.updateProduct(editedProduct)

                    // Enviar resultados a la actividad padre
                    val resultIntent = Intent()
                    resultIntent.putExtra("updatedProductId", productId)
                    setResult(Activity.RESULT_OK, resultIntent)

                    finish() // Cierra la actividad de edición después de guardar
                }
            }
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(
        precio: Int,
        cantidad: Int
    ): Boolean {
        return precio > 0 && cantidad > 0
    }
}
