import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.umarfarid.complaintbox.Complaint
import com.umarfarid.complaintbox.ComplaintViewHolder
import com.umarfarid.complaintbox.Details_of_compalint
import com.umarfarid.complaintbox.databinding.ItemComplaintBinding

class ComplaintAdapter(val items: ArrayList<Complaint>) : RecyclerView.Adapter<ComplaintViewHolder>() {

    private var sizes: List<Complaint> = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintViewHolder {

        return ComplaintViewHolder(
            ItemComplaintBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return sizes.size // Return the size of the sizes list
    }

    override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {

        val item = sizes[position]
        holder.binding.textView31.text = item.title?.toString()
        holder.binding.textView32.text = item.discription?.toString()
        holder.binding.textView28.text = item.date?.toString()
        holder.binding.namee.text = item.status?.toString()


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, Details_of_compalint::class.java)
            intent.putExtra("data", Gson().toJson(item))
            holder.itemView.context.startActivity(intent)
        }
    }

}
