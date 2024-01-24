package cl.litegames

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import data.ViewModel.ProductViewModel
import data.model.Producto

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var productViewModel: ProductViewModel
    private lateinit var textViewProductName: TextView
    private lateinit var textViewProductPrice: TextView
    private lateinit var textViewProductQuantity: TextView
    private lateinit var textViewProductDescription: TextView
    private lateinit var textViewProductCategory: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // Inicializar ViewModel y vistas
        backButton = findViewById(R.id.button_back_seeDetail)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        textViewProductName = findViewById(R.id.textView_detail_name)
        textViewProductPrice = findViewById(R.id.textView_detail_price)
        textViewProductQuantity = findViewById(R.id.textView_detail_quantity)
        textViewProductDescription = findViewById(R.id.textView_detail_description)
        textViewProductCategory = findViewById(R.id.textView_detail_category)

        // Obtener el ID del producto de la intención
        val productId = intent.getLongExtra("PRODUCT_ID", -1)

        if (productId.toInt() != -1) {
            // Obtener el LiveData del producto desde el ViewModel
            val productLiveData = productViewModel.getProductById(productId.toInt())

            // Observar el LiveData y actuar cuando cambie el valor
            productLiveData.observe(this) { product ->
                product?.let { showProductDetails(it) }
            }
        }
        backButton.setOnClickListener {
            val aboutIntent = Intent(this, InventoryActivity::class.java)
            startActivity(aboutIntent)
        }

    }
    private fun showProductDetails(product: Producto) {
        textViewProductName.text = getString(R.string.wordName) + ": " + product.nombre
        textViewProductPrice.text = getString(R.string.wordPrice) + ": " + product.precio
        textViewProductQuantity.text = getString(R.string.wordAmount) + ": " + product.cantidad
        textViewProductDescription.text =
            getString(R.string.wordDescription) + ": " + product.descripcion
        textViewProductCategory.text =
            getString(R.string.wordCategory) + ": " + product.categoria
    }
}
