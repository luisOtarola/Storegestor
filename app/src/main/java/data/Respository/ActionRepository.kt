package data.Respository

import data.Dao.ActionDao
import data.model.Action

class ActionRepository(private val actionDao: ActionDao) {

    suspend fun addAction(action: Action) {
        actionDao.insertAction(action)
    }
}
