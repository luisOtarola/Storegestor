package data.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import data.Dao.ActionDao
import data.DataBase.ProductDatabase
import data.model.Action
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActionViewModel(application: Application) : AndroidViewModel(application) {

    private val actionDao: ActionDao
    val allActions: LiveData<List<Action>>

    init {
        val database = ProductDatabase.getInstance(application)
        actionDao = database.actionDao()
        allActions = actionDao.getAllActions()
    }
    fun clearAllActions() {
        viewModelScope.launch(Dispatchers.IO) {
            actionDao.deleteAllActions()
        }
    }
}
