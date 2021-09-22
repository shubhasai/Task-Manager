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
            startActivity(Intent(this,MainActivity::class.java))
            Sawo(
                this,
                "0651d4d9-f3f3-49a3-b7de-6eca34704af8", // your api key
                "614b574309e2ab2081566855dla27xXeLSosIUUdK3b1k9AE"  // your api key secret

            ).login(
                "email", // can be one of 'email' or 'phone_number_sms'
                MainActivity::class.java.name // Callback class name
            )
            finish()
        }, 3000)
    }
}