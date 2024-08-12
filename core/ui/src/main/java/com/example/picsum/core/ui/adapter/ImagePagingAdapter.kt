package com.example.picsum.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.picsum.core.model.Image
import com.example.picsum.core.ui.databinding.ItemImageBinding


class ImagePagingAdapter: PagingDataAdapter<Image, ImageItemVh>(ImageDiffCallback()) {

    private var imageClickListener: ImageItemClickListener? = null

    override fun onBindViewHolder(holder: ImageItemVh, position: Int) {
        getItem(position)?.let {
            holder.bind(it) { item ->
                imageClickListener?.onImageClicked(item)
            }}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemVh {
        return ImageItemVh(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    private class ImageDiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.isFavorite == newItem.isFavorite &&
                    oldItem.url == newItem.url
        }
    }

    fun setImageClick(action: ImageItemClickListener){
        imageClickListener = action
    }

    interface ImageItemClickListener {
        fun onImageClicked(item: Image)
    }


}