package com.example.picsum.feature.randomPic

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.picsum.core.model.Image
import com.example.picsum.core.ui.adapter.BaseFragment
import com.example.picsum.core.ui.adapter.ImagePagingAdapter
import com.example.magentatest.core.ui.adapter.LoadStateAdapter
import com.example.picsum.feature.randomPic.databinding.RandomFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomFragment : BaseFragment<RandomFragmentBinding>(){
    private val adapter: ImagePagingAdapter = ImagePagingAdapter()
    private val viewModel: RandomViewModel by viewModels()

    override fun setBinding(): RandomFragmentBinding  = RandomFragmentBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding){
            with(adapter) {
                rvImages.apply {
                    postponeEnterTransition()
                    viewTreeObserver.addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
                }
                (rvImages.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                rvImages.adapter = withLoadStateHeaderAndFooter(
                    header = LoadStateAdapter { retry() },
                    footer = LoadStateAdapter { retry() }
                )
                addLoadStateListener {state->
                    binding.rvImages.isVisible = state.refresh != LoadState.Loading
                    binding.loader.isVisible = state.refresh == LoadState.Loading
                }
                setImageClick(object : ImagePagingAdapter.ImageItemClickListener{
                    override fun onImageClicked(item: Image) {
                        viewModel.toggleFavorite(item)
                    }
                })
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getImages().collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

}
