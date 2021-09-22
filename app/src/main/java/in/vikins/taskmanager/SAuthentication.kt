package `in`.vikins.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sawolabs.androidsdk.Sawo

class SAuthentication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sauthentication)
        Sawo(
            this,
            "b2e16bff-7647-4ab0-9a9a-32b5469a9896", // your api key
            "6146121147fda11932859386qNNvPYR4D2MZjoBeSvJU0wMQ"  // your api key secret
        ).login(
            "email", // can be one of 'email' or 'phone_number_sms'
            MainActivity::class.java.name // Callback class name
        )
    }
}