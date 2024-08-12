package com.example.magentatest.core.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.picsum.core.ui.R


class LoadStateAdapter (private val retry: () -> Unit) :
    LoadStateAdapter<LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder.create(parent, retry)
    }

}

class LoadStateViewHolder(
    view: View,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(view) {

    private val progress: ProgressBar = view.findViewById(R.id.progress_item)
    private val txtError: TextView = view.findViewById(R.id.error_item)
    private val btnError: AppCompatButton = view.findViewById(R.id.retry_btn)

    fun bind(loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                progress.isVisible = true
                txtError.isVisible = false
                btnError.isVisible = false
            }
            is LoadState.Error -> {
                progress.isVisible = false
                txtError.isVisible = true
                btnError.isVisible = true

                txtError.text =  loadState.error.localizedMessage
                btnError.setOnClickListener {
                    retry.invoke()
                }
            }
            is LoadState.NotLoading -> {
                progress.isVisible = false
                txtError.isVisible = false
                btnError.isVisible = false
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_load_state, parent, false)
            return LoadStateViewHolder(view, retry)
        }
    }
}