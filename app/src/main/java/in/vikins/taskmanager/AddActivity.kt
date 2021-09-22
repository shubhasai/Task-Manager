package `in`.vikins.taskmanager

import `in`.vikins.taskmanager.databinding.ActivityAddBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.sawolabs.androidsdk.Sawo


class AddActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddBinding
    private var mfirebaseinstance:FirebaseFirestore?=null
    private var uid:String = "null"
    private lateinit var title:String
    private lateinit var des:String
    private lateinit var date:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.clr1.setOnClickListener {
//            Uservalue.colour = R.color.yellow
//            Log.d("colour","y")
//        }
//        binding.clr2.setOnClickListener {
//            Uservalue.colour = R.color.orange
//            Log.d("colour","o")
//        }
//        binding.clr3.setOnClickListener {
//            Uservalue.colour = R.color.pink
//            Log.d("colour","p")
//        }
//        binding.clr4.setOnClickListener {
//            Uservalue.colour = R.color.green
//            Log.d("colour","g")
//        }
        binding.button.setOnClickListener {
            uid = intent.getStringExtra("uid").toString()
            title = binding.editTextTextPersonName.text.toString().trim()
            des = binding.editTextTextMultiLine.text.toString().trim()
            date = binding.editTextDate.text.toString().trim()
            mfirebaseinstance = FirebaseFirestore.getInstance()
            if(title.isEmpty()||des.isEmpty()||date.isEmpty()){
                Toast.makeText(this, "Enter All Field", Toast.LENGTH_SHORT).show()
            }
            else{
                adddata()
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }
    fun adddata(){
        val userid = uid
        val ttitle = title
        val tdes = des
        val tdt = date
        val taskdetails = task(ttitle,tdes,tdt)
        mfirebaseinstance?.collection("user")?.document(userid)?.collection("task")?.document()?.set(taskdetails)
    }
}