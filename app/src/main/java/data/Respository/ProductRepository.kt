package data.Respository

import androidx.lifecycle.LiveData
import data.Dao.ProductoDao
import data.model.Producto

class ProductRepository(private val productDao: ProductoDao) {

    val getAllData:  LiveData<List<Producto>> = productDao.getAllData()

    suspend fun addProduct(product: Producto){
        productDao.insertAll(product)
    }
}