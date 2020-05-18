package br.net.easify.arduinorelecontroll.view.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import br.net.easify.arduinorelecontroll.R
import br.net.easify.arduinorelecontroll.view.main.MainActivity
import br.net.easify.arduinorelecontroll.viewmodel.splashscreen.SplashScreenViewModel

class SplashScreenActivity : AppCompatActivity(), Runnable {

    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        viewModel = ViewModelProviders.of(this).get(SplashScreenViewModel::class.java)

        setLayoutFullScreen()

        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        run()
    }

    private fun setLayoutFullScreen() {
        val w = window
        w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        w.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        w.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    override fun run() {
        Handler().postDelayed({
            initApp()
        }, 3500)
    }

    fun initApp() {

        viewModel.updateDatabase()

        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        this.startActivity(intent)
        finish()
    }
}
