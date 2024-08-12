package com.example.picsum.feature.favoritePic

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import com.example.picsum.core.ui.adapter.BaseFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.picsum.core.ui.adapter.ImageSliderAdapter
import com.example.picsum.feature.favoritePic.databinding.FavoriteFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FavoriteFragmentBinding>(){
    private val adapter: ImageSliderAdapter = ImageSliderAdapter()
    private val viewModel: FavoriteViewModel by viewModels()



    override fun setBinding(): FavoriteFragmentBinding = FavoriteFragmentBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding){
            with(adapter) {
                imageSlider.adapter = this
            }

            TabLayoutMediator(tabLayout, imageSlider) { tab, position ->
                tab.text = "${position+1}"
            }.attach()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getImages().collectLatest { data->
                    binding.loader.isVisible = false
                    if(data.isEmpty()){
                        showCenteredAlertDialog(
                            requireContext(),"отсутсвуют избранные рисунки", "")
                    }else{
                        adapter.submitData(data.map { it.url })
                    }
                }
            }
        }
    }


    fun showCenteredAlertDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)

        val dialog = builder.create()

        dialog.window?.setGravity(Gravity.CENTER)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.window?.setFlags(
            WindowManager.LayoutParams.FLAG_DIM_BEHIND,
            WindowManager.LayoutParams.FLAG_DIM_BEHIND
        )

        val params = dialog.window?.attributes
        params?.dimAmount = 0.5f
        dialog.window?.attributes = params

        dialog.show()
    }




}