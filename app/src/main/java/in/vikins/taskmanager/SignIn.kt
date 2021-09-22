package `in`.vikins.taskmanager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.sawolabs.androidsdk.Sawo
import java.lang.Exception

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        Handler().postDelayed({
            startActivity(Intent(this,SAuthentication::class.java))
            finish()
        }, 3000)
    }
}