package com.example.rxjavamvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rxjavamvvm.R
import com.example.rxjavamvvm.databinding.ItemLayoutBinding
import com.example.rxjavamvvm.model.VolumeInfo

class RvAdapter : ListAdapter<VolumeInfo, RvAdapter.RvViewHolder>(DiffCallBack()) {

    private class DiffCallBack : DiffUtil.ItemCallback<VolumeInfo>() {
        override fun areItemsTheSame(oldItem: VolumeInfo, newItem: VolumeInfo): Boolean {
            return oldItem.volumeInfo.title == newItem.volumeInfo.title
        }

        override fun areContentsTheSame(oldItem: VolumeInfo, newItem: VolumeInfo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        return RvViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RvViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(volumeInfo: VolumeInfo) {
            binding.apply {
                Glide.with(thumbImageView)
                    .load(volumeInfo.volumeInfo.imageLinks.smallThumbnail)
                    .placeholder(R.drawable.image_back)
                    .circleCrop()
                    .into(thumbImageView)

                tvTitle.text = volumeInfo.volumeInfo.title
                tvDescription.text = volumeInfo.volumeInfo.description
                tvPublisher.text = volumeInfo.volumeInfo.publisher
            }
        }
    }
}