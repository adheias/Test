package com.dicoding.picodiploma.submission2bfaa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.submission2bfaa.databinding.ItemRowUserBinding
import com.dicoding.picodiploma.submission2bfaa.model.User

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    var mData = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(items: ArrayList<User>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(user.avatar)
                        .into(imgItemPhoto)

                tvUsername.text = user.username
                tvName.text = user.location

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}