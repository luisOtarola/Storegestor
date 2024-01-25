package data.Respository

import androidx.lifecycle.LiveData
import data.Dao.ProductoDao
import data.model.Producto
import data.model.Action

class ProductRepository(private val productDao: ProductoDao, private val actionRepository: ActionRepository) {

    val getAllData: LiveData<List<Producto>> = productDao.getAllData()

    suspend fun addProduct(product: Producto) {
        productDao.insertAll(product)
        actionRepository.addAction(Action(nombre = product.nombre, accion = "Agregado"))
    }

    suspend fun deleteProduct(product: Producto) {
        productDao.delete(product)
        actionRepository.addAction(Action(nombre = product.nombre, accion = "Eliminado"))
    }

    fun getProductById(productId: Int): LiveData<Producto?> {
        return productDao.getProductById(productId)
    }

    suspend fun updateProduct(product: Producto) {
        productDao.update(product)
        actionRepository.addAction(Action(nombre = product.nombre, accion = "Modificado"))
    }

}







