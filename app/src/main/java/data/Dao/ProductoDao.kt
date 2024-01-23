package data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import data.model.Producto

@Dao
interface ProductoDao {

    @Query("SELECT * FROM productos ORDER By id ASC")
    fun getAll(): List<Producto>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg productos: Producto)

    @Update
    fun update(producto: Producto)

    @Delete
    fun delete(producto: Producto)
}
