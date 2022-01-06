package com.projectnewm.ui.stars

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.projectnewm.R
import com.projectnewm.databinding.FragmentStarsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarsFragment : Fragment(R.layout.fragment_stars) {

    private val viewModel: StarsViewModel by viewModels()

    private val binding: FragmentStarsBinding by viewBinding(FragmentStarsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.text.observe(viewLifecycleOwner) {
            binding.textStars.text = it
        }
    }
}