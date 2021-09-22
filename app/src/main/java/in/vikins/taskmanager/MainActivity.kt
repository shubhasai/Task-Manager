package `in`.vikins.taskmanager

import `in`.vikins.taskmanager.databinding.ActivityMainBinding
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import com.sawolabs.androidsdk.LOGIN_SUCCESS_MESSAGE
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import org.json.JSONObject


class MainActivity : AppCompatActivity(),taskadapter.taskItemClicked {
    lateinit var adapter: taskadapter
    lateinit var binding: ActivityMainBinding
    lateinit var user_data: JSONObject
    lateinit var Task: ArrayList<task>
    lateinit var TempTask: ArrayList<task>
    lateinit var Builder:AlertDialog.Builder
    private var mfirebaseinstance: FirebaseFirestore? = null
    private var user_id:String = "null"
    override fun onCreate(savedInstanceState: Bundle?) {
//        Uservalue.signin = true
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mfirebaseinstance = FirebaseFirestore.getInstance()
        val msg = intent.getStringExtra(LOGIN_SUCCESS_MESSAGE)
        try {
            user_data = JSONObject(msg)
        } catch (e: Exception) {
            Toast.makeText(this, "Some Error Occurred! Please try again.", Toast.LENGTH_LONG).show()
        }
        user_id = user_data.getString("user_id")
        binding.recyclerView.layoutManager =  StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        Task = arrayListOf()
        adapter = taskadapter(Task,this)
//        binding.imageView3.setOnClickListener {
//            Uservalue.signin = false
//            finish()
//        }
        binding.recyclerView.adapter = adapter
        binding.imageView2.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("uid", user_id)
            startActivity(intent)
        }
        eventchangelistener(user_id)

        TempTask = arrayListOf()
        binding.serchview.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                queryresultfinal(query)
                Log.d("searcherror","${query}")
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("searcherror","${newText}")
                queryresult(newText)
                return true
            }
        })
    }
    private fun queryresult(txt:String?){
        adapter = taskadapter(TempTask,this)
        Log.d("searcherror","Query fun called")
        Log.d("searcherror","${txt}")
        val queryres:Query? = txt?.let {
            mfirebaseinstance?.collection("user")?.document(user_id)?.collection("task")
                ?.whereGreaterThanOrEqualTo("date", txt)
        }
        queryres?.addSnapshotListener { value, error ->
            Task.clear()
            for(document in value?.documents!!){
                val res = document.toObject(task::class.java)
                Log.d("result", res?.title.toString())
                Task.add(res!!)
            }
            Log.d("result", value?.size().toString())
//            Task.clear()
            adapter.addList(Task)
            adapter.notifyDataSetChanged()


        }
    }
    private fun queryresultfinal(txt:String?){
        adapter = taskadapter(TempTask,this)
        Log.d("searcherror","Query fun called")
        Log.d("searcherror","${txt}")
        val queryres:Query? = txt?.let {
            mfirebaseinstance?.collection("user")?.document(user_id)?.collection("task")
                ?.whereEqualTo("date", txt)
        }
        queryres?.addSnapshotListener { value, error ->
            Task.clear()
            for(document in value?.documents!!){
                val res = document.toObject(task::class.java)
                Log.d("result", res?.title.toString())
                Task.add(res!!)
            }
            Log.d("result", value?.size().toString())
//            Task.clear()
            adapter.addList(Task)
            adapter.notifyDataSetChanged()


        }
    }
    private fun eventchangelistener(id: String) {
        mfirebaseinstance?.collection("user")?.document(id)?.collection("task")
            ?.addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }
                    for (mfirebaseinstance: DocumentChange in value?.documentChanges!!) {
                        if (mfirebaseinstance.type == DocumentChange.Type.ADDED) {
                            Task.add(mfirebaseinstance.document.toObject(task::class.java))
                        }
                    }
                    adapter.notifyDataSetChanged()
                }


            })
    }
    override fun onitemClicked(itemlist: task) {

        Builder = AlertDialog.Builder(this)
        Builder.setTitle("Alert")
            .setMessage("Are You sure? Do You Want to Delete")
            .setCancelable(true)
            .setPositiveButton("YES"){dialogInterface,it->
                mfirebaseinstance?.collection("user")?.document(user_id)?.collection("task")
                    ?.whereEqualTo("title", itemlist.title)
                    ?.whereEqualTo("des", itemlist.des)
                    ?.whereEqualTo("date", itemlist.date)
                    ?.get()
                    ?.addOnSuccessListener { documents ->
                        for (document in documents) {
                            mfirebaseinstance?.collection("user")?.document(user_id)?.collection("task")!!
                                .document(document.id).delete()
                            Task.clear()
                            eventchangelistener(user_id)
                        }
                    }
                    ?.addOnFailureListener { exception ->
                        Log.w(TAG, "Error getting documents: ", exception)
                    }
            }
            .setNegativeButton("NO"){dialogInterface,it->
                dialogInterface.cancel()
            }
            .show()
    }

    override fun onBackPressed() {
        if (!binding.serchview.isIconified()) {
            binding.serchview.onActionViewCollapsed();
        } else {
            super.onBackPressed();
        }
    }

}