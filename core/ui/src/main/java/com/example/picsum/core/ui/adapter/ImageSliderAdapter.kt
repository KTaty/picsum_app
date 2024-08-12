package com.example.picsum.core.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.picsum.core.ui.R
import com.github.chrisbanes.photoview.PhotoView


class ImageSliderAdapter: RecyclerView.Adapter<ImageSliderAdapter.SlideItem>() {
    private var urls = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideItem {
        val item = PhotoView(parent.context)//ImageView(parent.context)
        item.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return SlideItem(item)
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    fun submitData(urls: List<String>){
       this.urls= urls
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SlideItem, position: Int) {
        holder.bind( urls[position])
    }


    inner class SlideItem(view: View):RecyclerView.ViewHolder(view){
        private val imageView = view as ImageView

         fun bind(url: String){
             Glide.with(imageView.context)
                 .load(url)
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .transition(DrawableTransitionOptions.withCrossFade())
                 .downsample(DownsampleStrategy.AT_MOST)
                 .placeholder(R.drawable.ic_item_image)
                 .into(imageView)
         }
    }


}