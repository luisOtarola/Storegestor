package data.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import data.DataBase.ProductDatabase
import data.Respository.ProductRepository
import data.model.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val getAllData: LiveData<List<Producto>>
    private val repository: ProductRepository

    init {
        val productDao = ProductDatabase.getInstance(application).productoDao()
        repository = ProductRepository(productDao)
        getAllData = repository.getAllData
    }

    fun addProduct(producto: Producto) {
        viewModelScope.launch(Dispatchers.IO) { repository.addProduct(producto) }
    }
}