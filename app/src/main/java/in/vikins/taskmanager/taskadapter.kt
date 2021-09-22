package `in`.vikins.taskmanager

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class taskadapter(private var taskdts:ArrayList<task>,val listener:taskItemClicked):RecyclerView.Adapter<taskadapter.MyviewHolder>(){
    class MyviewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val cardview:CardView= itemView.findViewById(R.id.cardviewrc)
        val title: TextView = itemView.findViewById(R.id.textView4)
        val des: TextView = itemView.findViewById(R.id.textView6)
        val date: TextView = itemView.findViewById(R.id.textView7)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_task_view,parent,false)
        val viewholder = MyviewHolder(itemView)
        itemView.setOnClickListener{
            listener.onitemClicked(taskdts[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        val TASK:task = taskdts[position]
        holder.title.text = TASK.title
        holder.date.text = "Date:- "+ TASK.date
        holder.des.text = TASK.des
        val clr = holder.cardview.context.resources.getColor(R.color.yellow)
        holder.cardview.setCardBackgroundColor(clr)
    }

    override fun getItemCount(): Int {
        return taskdts.size
    }

    fun addList(tempTask: ArrayList<task>) {
        taskdts = tempTask
        Log.d("result newlist", tempTask.size.toString())
        notifyDataSetChanged()
    }

    interface taskItemClicked {
        fun onitemClicked(itemlist: task) {

        }
    }
}