package cl.litegames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var inventoryButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inventoryButton = findViewById(R.id.button_inventoryList) as Button
        val toolbar: Toolbar = findViewById(R.id.toolbar_home)

        setSupportActionBar(toolbar)
        supportActionBar?.title = null

        inventoryButton.setOnClickListener {
            val aboutIntent = Intent(this, InventoryActivity::class.java)
            startActivity(aboutIntent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search->{
                val aboutAct = Intent(this, AboutActivity::class.java)
                startActivity(aboutAct)
                return true
            }
            R.id.action_preferences->{
                val aboutAct = Intent(this, SettingsActivity::class.java)
                startActivity(aboutAct)
                return true
            }
            R.id.action_exit->{
                finishAffinity()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}