package com.projectnewm.ui.community

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.projectnewm.R
import com.projectnewm.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment(R.layout.fragment_community) {

    private val viewModel: CommunityViewModel by viewModels()

    private val binding: FragmentCommunityBinding by viewBinding(FragmentCommunityBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.text.observe(viewLifecycleOwner) {
            binding.textCommunity.text = it
        }
    }
}