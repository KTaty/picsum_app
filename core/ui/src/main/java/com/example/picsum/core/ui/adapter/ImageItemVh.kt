package com.example.picsum.core.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.picsum.core.model.Image
import com.example.picsum.core.ui.R
import com.example.picsum.core.ui.databinding.ItemImageBinding

class ImageItemVh(private val binding: ItemImageBinding)  : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Image, onLikeClickListener: (item: Image) -> Unit){
        with(binding){
            Glide.with(itemView.context)
                .load(item.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .downsample(DownsampleStrategy.AT_MOST)
                .placeholder(R.drawable.ic_item_image)
                .into(imageItem)

            likeButton.apply {
                imageTintMode = PorterDuff.Mode.SRC_ATOP
                imageTintList = if (item.isFavorite) ColorStateList.valueOf(Color.RED)
                else ColorStateList.valueOf(Color.GRAY)
                setOnClickListener {
                    root.performClick()
                }
            }
            root.setOnClickListener {
                onLikeClickListener(item)

            }
        }



    }

}