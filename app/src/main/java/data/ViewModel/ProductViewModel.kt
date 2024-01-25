package data.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import data.DataBase.ProductDatabase
import data.Respository.ActionRepository
import data.Respository.ProductRepository
import data.model.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository
    private val actionRepository: ActionRepository
    val getAllData: LiveData<List<Producto>>

    init {
        val productDao = ProductDatabase.getInstance(application).productoDao()
        val actionDao = ProductDatabase.getInstance(application).actionDao()
        actionRepository = ActionRepository(actionDao)
        repository = ProductRepository(productDao, actionRepository)
        getAllData = repository.getAllData
    }

    fun addProduct(producto: Producto) {
        viewModelScope.launch(Dispatchers.IO) { repository.addProduct(producto) }
    }
    fun deleteProduct(producto: Producto) {
        viewModelScope.launch(Dispatchers.IO) { repository.deleteProduct(producto) }
    }

    fun getProductById(productId: Int): LiveData<Producto?> {
        return repository.getProductById(productId)
    }

    fun updateProduct(producto: Producto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProduct(producto)
        }
    }

}