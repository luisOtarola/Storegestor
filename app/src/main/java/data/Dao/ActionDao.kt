package data.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.model.Action

@Dao
interface ActionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAction(action: Action?): Long?

    @Query("SELECT * FROM actions ORDER BY id ASC")
    fun getAllActions(): LiveData<List<Action>>
    @Query("DELETE FROM actions")
    fun deleteAllActions()
}
