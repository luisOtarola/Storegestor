package cl.litegames

import adapter.ActionAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import data.ViewModel.ActionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ActionListActivity : AppCompatActivity() {

    private lateinit var actionViewModel: ActionViewModel
    private lateinit var actionAdapter: ActionAdapter
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_list)

        val listView: ListView = findViewById(R.id.listView_actionList)
        actionAdapter = ActionAdapter(this, R.layout.action_list, ArrayList())
        listView.adapter = actionAdapter

        backButton = findViewById(R.id.button_back_actionList)

        actionViewModel = ViewModelProvider(this).get(ActionViewModel::class.java)
        actionViewModel.allActions.observe(this) { actions ->
            actionAdapter.clear()
            actionAdapter.addAll(actions)
            actionAdapter.notifyDataSetChanged()
        }

        backButton.setOnClickListener {
            val aboutIntent = Intent(this, MainActivity::class.java)
            startActivity(aboutIntent)
        }

        val clearActionsButton: ImageButton = findViewById(R.id.button_clear_actions)
        clearActionsButton.setOnClickListener {
            clearAllActions()
        }
    }
    private fun clearAllActions() {
        GlobalScope.launch(Dispatchers.IO) {
            actionViewModel.clearAllActions()
        }
    }
}

