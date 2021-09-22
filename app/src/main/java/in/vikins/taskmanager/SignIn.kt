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
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        Handler().postDelayed({

                Sawo(
                    this,
                    "b2e16bff-7647-4ab0-9a9a-32b5469a9896", // your api key
                    "6146121147fda11932859386qNNvPYR4D2MZjoBeSvJU0wMQ"  // your api key secret
                ).login(
                    "email", // can be one of 'email' or 'phone_number_sms'
                    MainActivity::class.java.name // Callback class name
                )
                finish()
            }, 5000)
    }
}