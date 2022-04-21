package io.github.loshine.fitness

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SimpleTextAdapter(
    private val data: List<String>,
    private val onItemClickListener: (View, Int, String) -> Unit
) : RecyclerView.Adapter<SimpleTextAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_simple_text, parent, false)

        return ViewHolder(view).apply {
            view.setOnClickListener {
                onItemClickListener.invoke(it, bindingAdapterPosition, data[bindingAdapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView = itemView.findViewById<TextView>(R.id.text)

        fun bind(item: String) {
            textView.text = item
        }
    }
}