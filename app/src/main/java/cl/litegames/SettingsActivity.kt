package cl.litegames

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Busca la preferencia directamente en el fragmento
        val settingsFragment = supportFragmentManager.findFragmentById(R.id.settings) as? SettingsFragment
        val sortOrderPreference = settingsFragment?.findPreference<ListPreference>("sortOrder")

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Cargar el valor almacenado en SharedPreferences y establecerlo en ListPreference
        val savedSortOrder = sharedPreferences.getString("sortOrder", "default_value")
        sortOrderPreference?.value = savedSortOrder
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}
