package cl.litegames

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.view.animation.AnimationUtils

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val imageView = findViewById<ImageView>(R.id.img_splash)

        // Cargar la animación de fade_in
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        // Asignar la animación a la vista que deseas animar
        imageView.startAnimation(fadeInAnimation)

        // Agregar un temporizador para simular la carga
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
    companion object {
        private const val SPLASH_TIME_OUT: Long = 3000 // 3 segundos
    }
}